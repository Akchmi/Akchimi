<template>
  <div id="main-container" class="container">
    <div id="join" v-if="!sessionCamera">
      <div id="join-dialog" class="enterRoom__container">
        <div>
          <button @click="$router.push('/main')">나가기</button>
          <video id="my-video" autoplay="true" :srcObject="myVideo"></video>
          <div class="form-group">
            <div class="enterInfo">
              <div>
                <label>참여자 이름</label>
                <input
                  v-model="myUserName"
                  class="enterInfoInput"
                  type="text"
                  required
                />
              </div>
              <!-- <div>
                <label>강의실 번호</label>
                <input
                  v-model="mySessionId"
                  class="enterInfoInput"
                  type="text"
                  required
                />
              </div> -->
            </div>
            <div class="enterButton">
              <button @click="joinSession()">강의실 입장</button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div id="session" v-if="sessionCamera">
      <!-- 탑 (세션번호) -->
      <div id="session-header" class="top-container"></div>
      <!-- 바디 (왼쪽: 작은화면, 메트로놈 / 가운데: 큰메인화면 / 오른쪽: 채팅창) -->
      <div class="body-container">
        <!-- 바디 왼쪽: 작은화면, 메트로놈 -->
        <div class="body-left">
          <div class="aditional-function">
            <!-- {{ subscribers }} -->
            <div id="live-screens" class="" @click="changeMainScreen">
              <!-- {{ subscribers }} -->
              <user-video :stream-manager="publisher" />
              <user-video
                id="participants"
                v-for="sub in subscribers"
                :key="sub.stream.connection.connectionId"
                :stream-manager="sub"
              />
              <div id="shared-video" class="row panel panel-default">
                <!-- <div class="panel-heading">User Screens</div> -->
                <div class="panel-body" id="container-screens"></div>
              </div>
            </div>
          </div>
        </div>
        <!-- 바디 가운데: 큰메인화면 -->
        <div id="video-container" class="video-box">
          <user-video :stream-manager="publisher" />
          <TheMetronome v-if="metronomePopState" />
          <TunerApp v-if="popState" @close="changePopState()" />
        </div>
        <!-- 바디 오른쪽: 채팅창 -->
        <div class="message-box">
          <div class="messages-container" ref="chatroom">
            <div
              v-for="(message, index) in receivedMessages"
              :class="message.className"
              :key="index"
            >
              <div class="words-container">
                {{ message.message }}
              </div>
            </div>
          </div>
          <div class="message-input-container">
            <input
              class="message-input-form"
              type="text"
              v-model="newMessage"
              @keyup.enter="sendMessage"
            />
            <button class="message-send-btn" @click="sendMessage">전송</button>
          </div>
        </div>
      </div>
      <!-- 바텀: 튜너, 화면공유, 나가기 버튼 -->
      <div class="bottom-container">
        <div class="button-box">
          <button class="bottom-button" @click="changeMetronomePopState()">
            매트로놈
          </button>
          <button class="bottom-button" @click="changePopState()">튜너</button>
          <button
            id="buttonScreenShare"
            class="bottom-button"
            @click="publishScreenShare"
            v-if="sessionScreen"
          >
            화면공유
          </button>
          <button v-if="audioEnabled"
            class="bottom-button"
            id="buttonMute"
            @click="muteSound"
            >음소거</button>
          <button v-else
            class="bottom-button"
            id="buttonMute"
            @click="muteSound">
          음소거 해제</button>
          <button
            class="bottom-button"
            id="buttonLeaveSession"
            @click="leaveSession"
          >
            강의 떠나기
          </button>
          
        </div>
      </div>
      <!-- <user-video :stream-manager="mainStreamManager" />
          <user-video :stream-manager="publisher" /> -->
      <!-- <div id="main-video" class="col-md-6">
        <user-video :stream-manager="mainStreamManager" />
      </div> -->

      <!-- <div id="main-video" class="col-md-6">
        <p></p>
        <video autoplay playsinline="true"></video>
      </div>
      <div class="col-md-6">
        <div class="row panel panel-default">
          <div class="panel-heading">User Screens</div>
          <div class="panel-body" id="container-screens"></div>
        </div>
      </div> -->

      <!-- <div id="video-container" class="col-md-6">
        <user-video
          :stream-manager="publisher"
          @click="updateMainVideoStreamManager(publisher)"
        />
        <user-video
          v-for="sub in subscribers"
          :key="sub.stream.connection.connectionId"
          :stream-manager="sub"
          @click="updateMainVideoStreamManager(sub)"
        />
      </div> -->
      <!-- <user-video
        v-for="sub in subscribers"
        :key="sub.stream.connection.connectionId"
        :stream-manager="sub"
        @click="updateMainVideoStreamManager(sub)"
      /> -->
    </div>
  </div>
</template>

<script>
import { OpenVidu } from "openvidu-browser";
import UserVideo from "../components/LiveMeeting/UserVideo";
import { useRoute } from "vue-router";
import TheMetronome from "@/components/LiveMeeting/TheMetronome";
import TunerApp from "../components/LiveMeeting/TunerApp.vue";
import axios from "axios";
// import api from "@/api/axios";
// import A210URL from "@/api/axios";
axios.defaults.headers.post["Content-Type"] = "application/json";

const APPLICATION_SERVER_URL = "https://i9a210.p.ssafy.io:3000/api/lectures/";

export default {
  name: "App",

  components: {
    UserVideo,
    TunerApp,
    TheMetronome,
  },

  data() {
    return {
      // OpenVidu objects`
      OVCamera: undefined,
      OVScreen: undefined,
      sessionCamera: undefined,
      sessionScreen: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],
      receivedMessages: [],
      sentMessages: [],
      newMessage: "",
      screensharing: false,

      // Join form
      mySessionId: "",
      sender: 0,

      myUserName: JSON.parse(localStorage.getItem("vuex")).common.name,
      popState: false,
      metronomePopState: false,

      // 대기 화면 내 비디오 확인
      myVideo: null,
      audioEnabled: true,
    };
  },
  created() {
    // App.vue가 생성되면 소켓 연결을 시도합니다.
    // RoomKey axios호출로 수정
    const route = useRoute();
    this.mySessionId = route.params.lectureId;

    this.getCamera();
    // console.log(this.roomId + "!!!!!!!!!!!!!");
  },

  methods: {
    // 대기화면에서 카메라 가져오는 함수
    async getCamera() {
      let stream = null;

      try {
        stream = await navigator.mediaDevices.getUserMedia({
          audio: true,
          video: true,
        });

        this.myVideo = stream;
      } catch (error) {
        alert("카메라 접근 실패");
      }
    },

    sendMessage() {
      if (this.sessionCamera) {
        this.sessionCamera
          .signal({
            data: this.myUserName + " : " + this.newMessage, // Any string (optional)
            to: [], // Array of Connection objects (optional. Broadcast to everyone if empty)
            type: "my-chat",
            // The type of message (optional)
          })
          .then(() => {
            // console.log("Message successfully sent");
            this.newMessage = "";
          })
          .catch((error) => {
            console.error(error);
          });
      } else {
        console.error("Session or data channel is not available.");
      }
    },
    handleMessage(message) {
      // Extract the message data from the incoming signal object
      const receivedMessage = message.data;

      // Push the received message into the messages array
      this.messages.push(receivedMessage);
    },
    scrollToBottom() {
      this.$nextTick(() => {
        const chatroom = this.$refs.chatroom;
        chatroom.scrollTop = chatroom.scrollHeight;
      });
    },
    changeMainScreen(event) {
      const mainVideoDiv = document.querySelector("#video-container");
      const mainVideo = mainVideoDiv.querySelector("video");
      const selectedVideo = event.target;

      if (
        selectedVideo.srcObject != null &&
        mainVideo.srcObject !== selectedVideo.srcObject
      ) {
        const mainVideoContainer = document.querySelector("#video-container");
        mainVideoContainer.style.display = "none";

        mainVideo.srcObject = selectedVideo.srcObject;
        mainVideoContainer.style.display = "block";
      }
    },
    appendUserData(videoElement, connection) {
      var nodeId;

      if (typeof connection === "string") {
        nodeId = connection;
      } else {
        nodeId = connection.connectionId;
      }
      var dataNode = document.createElement("div");
      dataNode.className = "data-node";
      dataNode.id = "data-" + nodeId;
      videoElement.parentNode.insertBefore(dataNode, videoElement.nextSibling);
    },

    publishScreenShare() {
      // --- 9.1) To create a publisherScreen set the property 'videoSource' to 'screen'
      var publisherScreen = this.OVScreen.initPublisher("container-screens", {
        videoSource: "screen",
      });

      // --- 9.2) Publish the screen share stream only after the user grants permission to the browser
      publisherScreen.once("accessAllowed", (event) => {
        document.getElementById("buttonScreenShare").style.visibility =
          "hidden";
        this.screensharing = true;
        // event.element["muted"] = true;
        console.log(event);
        // If the user closes the shared window or stops sharing it, unpublish the stream
        publisherScreen.stream
          .getMediaStream()
          .getVideoTracks()[0]
          .addEventListener("ended", () => {
            // 공유중지 버튼 누르면
            // console.log(
            //   'User pressed the "Stop sharing" button!!!!!!!!!!!!!!!!!'
            // );
            this.sessionScreen.unpublish(publisherScreen);
            document.getElementById("buttonScreenShare").style.visibility =
              "visible";
            this.screensharing = false;
            const screenDiv = document.querySelector("#live-screens");
            const screen = screenDiv
              .querySelector("#user-video")
              .querySelector("video");
            const mainVideoContainer =
              document.querySelector("#video-container");
            const mainVideo = mainVideoContainer.querySelector("video");
            mainVideoContainer.style.display = "none";

            mainVideo.srcObject = screen.srcObject;
            mainVideoContainer.style.display = "block";
          });
        this.sessionScreen.publish(publisherScreen);
      });

      publisherScreen.on("videoElementCreated", (event) => {
        this.appendUserData(event.element, this.sessionScreen.connection);
        event.element["muted"] = true;
      });

      publisherScreen.once("accessDenied", (event) => {
        console.error("Screen Share: Access Denied");

        console.log(event);
      });
    },

    joinSession() {
      // --- 1) Get an OpenVidu object ---
      this.OVCamera = new OpenVidu();
      this.OVScreen = new OpenVidu();

      // --- 2) Init a session ---
      this.sessionCamera = this.OVCamera.initSession();
      this.sessionScreen = this.OVScreen.initSession();

      // --- 3) Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.sessionCamera.on("streamCreated", (event) => {
        // console.log(event);
        // console.log(this.sessionScreen);
        if (event.stream.typeOfVideo == "CAMERA") {
          // Subscribe to the Stream to receive it. HTML video will be appended to element with 'container-cameras' id
          var subscriber = this.sessionCamera.subscribe(
            event.stream,
            "container-cameras"
          );
          this.subscribers.push(subscriber);
          // When the HTML video has been appended to DOM...
          subscriber.on("videoElementCreated", (event) => {
            // Add a new <p> element for the user's nickname just below its video
            this.appendUserData(event.element, subscriber.stream.connection);
          });
        }
      });
      console.log(this.sessionScreen);
      this.sessionScreen.on("streamCreated", (event) => {
        // console.log(this);
        // console.log(this.sessionScreen);
        if (
          this.sessionScreen != null &&
          event.stream.typeOfVideo == "SCREEN"
        ) {
          // Subscribe to the Stream to receive it. HTML video will be appended to element with 'container-screens' id
          var subscriberScreen = this.sessionScreen.subscribe(
            event.stream,
            "container-screens"
          );
          // When the HTML video has been appended to DOM...
          subscriberScreen.on("videoElementCreated", (event) => {
            // Add a new <p> element for the user's nickname just below its video
            this.appendUserData(
              event.element,
              subscriberScreen.stream.connection
            );
          });
        }
      });
      this.sessionCamera.on("signal", (event) => {
        const receivedMessage = event.data;
        // console.log(event.from);
        if (receivedMessage != this.myUserName + " : " + this.newMessage) {
          this.receivedMessages.push({
            message: receivedMessage,
            className: "left",
          });
        } else {
          this.receivedMessages.push({
            message: receivedMessage,
            className: "rightviduchat",
          });
        }
      });

      // On every Stream destroyed...
      this.sessionCamera.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
      });

      // On every asynchronous exception...
      this.sessionCamera.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      // --- 4) Connect to the session with a valid user token ---

      // Get a token from the OpenVidu deployment
      this.getToken(this.mySessionId).then((token) => {
        // First param is the token. Second param can be retrieved by every user on event
        // 'streamCreated' (property Stream.connection.data), and will be appended to DOM as the user's nickname
        this.sessionCamera
          .connect(token, { clientData: this.myUserName })
          .then(() => {
            // --- 5) Get your own camera stream with the desired properties ---

            // Init a publisher passing undefined as targetElement (we don't want OpenVidu to insert a video
            // element: we will manage it on our own) and with the desired properties
            let publisher = this.OVCamera.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: "640x480", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });

            publisher.on("videoElementCreated", function (event) {
              this.initMainVideo(event.element, this.myUserName);
              this.appendUserData(event.element, this.myUserName);
              event.element["muted"] = true;
            });

            // Set the main video in the page to display our webcam and store our Publisher
            this.mainStreamManager = publisher;
            this.publisher = publisher;

            // --- 6) Publish your stream ---

            this.sessionCamera.publish(this.publisher);
          })
          .catch((error) => {
            console.log(error);
          });
      });
      this.getToken(this.mySessionId).then((tokenScreen) => {
        // Create a token for screen share
        this.sessionScreen
          .connect(tokenScreen, { clientData: this.myUserName })
          .then(() => {
            document.getElementById("buttonScreenShare").style.visibility =
              "visible";
            // console.log("Session screen connected");
          })
          .catch((error) => {
            console.warn(
              // "There was an error connecting to the session for screen share:",
              // error.code,
              error.message
            );
          });
      });

      window.addEventListener("beforeunload", this.leaveSession);
    },

    leaveSession() {
      window.removeEventListener("beforeunload", this.leaveSession);
      // --- 7) Leave the session by calling 'disconnect' method over the Session object ---
      if (this.sessionCamera) {
        this.sessionCamera.disconnect();
      }
      if (this.screensharing == true) {
        this.sessionScreen.disconnect();
      }

      // Empty all properties...
      this.sessionCamera = undefined;
      this.sessionScreen = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OVCamera = undefined;
      this.OVScreen = undefined;
      this.screensharing = false;
      this.screens = [];
      // Remove beforeunload listener
    },

    muteSound(){
      if(this.audioEnabled==true){
        this.audioEnabled=false;
      }else{
        this.audioEnabled=true;
      }
      this.publisher.publishAudio(this.audioEnabled);
    },

    updateMainVideoStreamManager(stream) {
      if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
    },

    async getToken(mySessionId) {
      const sessionId = await this.createSession(mySessionId);
      return await this.createToken(sessionId);
    },

    async createSession(sessionId) {
      const response = await axios.post(
        APPLICATION_SERVER_URL + "sessions",
        { customSessionId: sessionId },
        {
          headers: { "Content-Type": "application/json" },
        }
      );
      return response.data; // The sessionId
    },

    async createToken(sessionId) {
      const response = await axios.post(
        APPLICATION_SERVER_URL + "sessions/" + sessionId + "/connections",
        {},
        {
          headers: { "Content-Type": "application/json" },
        }
      );
      return response.data; // The token
    },
    changePopState() {
      this.popState = !this.popState;
    },
    changeMetronomePopState() {
      this.metronomePopState = !this.metronomePopState;
    },
  },
  updated() {
    if (this.$refs.chatroom != null) {
      this.scrollToBottom();
    }
  },
  beforeUnmount() {
    // `this`를 통해 컴포넌트 인스턴스에 접근할 수 있습니다.
    this.leaveSession();
    if (this.myVideo != null) {
      const tracks = this.myVideo.getTracks();

      tracks.forEach((track) => {
        track.stop();
      });

      this.myVideo = null;
    }
    // console.log("mounted()가 호출 되었습니다:", this);
  },
};
</script>

<style>
#join {
  display: flex;
  justify-content: center;
}

#my-video {
  width: 600px;
  height: 450px;
  margin: 10px;
}

.enterRoom__container {
  width: 550px;
  border: 3px #edd9b7 solid;
  border-radius: 20px;
  padding: 20px 50px 30px 50px;
  display: flex;
  justify-content: center;
}

.form-group {
  display: flex;
  flex-direction: column;
}

.enterInfoInput {
  border: 3px #edd9b7 solid;
  border-radius: 2em;
  margin-left: 10px;
  padding-left: 15px;
}

.enterInfo {
  display: flex;
  justify-content: space-around;
  margin: 10px;
}

.enterButton {
  display: flex;
  justify-content: center;
  margin-top: 10px;
}

.top-container {
  width: 98vw;
  height: 5vh;
  font-weight: bolder;
  font-size: larger;
  position: relative;
  box-sizing: border-box;
  padding: 10px;
  /* // border: solid 1px red; */
}
.body-container {
  width: 99vw;
  height: 85vh;
  display: flex;
  position: relative;
  z-index: 5;
}
.bottom-container {
  height: 10vh;
  width: 98vw;
  background-color: white;
  position: relative;
  z-index: 10;
  display: flex;
  justify-content: center;
  align-items: center;
}

video {
  width: 100%;
}
.split-screen {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.body-left {
  width: 20%;
  float: left;
  display: flex;
  justify-content: center;
  align-items: center;
}
.aditional-function {
  width: 98%;
  height: 99%;
  box-sizing: border-box;
  background-color: white;
  box-shadow: 1px 2px 1px 2px rgb(199, 199, 199);
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow-y: scroll;
}
#live-screens {
  width: 90%;
  margin-top: 10px;
}
.panel-body {
  width: 100%;
}
.video-box {
  width: 60%;
  float: center;
}
.message-box {
  width: 20%;
  height: 100%;
  float: right;
  box-sizing: border-box;
  position: relative;
  background-color: #f5f5f5;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-evenly;
}
.messages-container {
  width: 100%;
  height: 85%;
  box-sizing: border-box;
  overflow: auto;
}
.message-input-container {
  width: 100%;
  height: 10%;
  bottom: 0;
  position: sticky;
  display: flex;
  justify-content: center;
  align-items: center;
}
.message-input-form {
  width: 70%;
  height: 89%;
  border: 2px solid #edd9b7;
  border-radius: 10px;
  margin-right: 5px;
  outline: none;
}
.message-send-btn {
  width: 19%;
  height: 90%;
  border-radius: 10px;
}
.tuner-container {
  position: absolute;
  width: 20%;
  height: 40%;
  bottom: 1%;
  left: 21%;
  display: flex;
  justify-content: center;
  z-index: 20;
}
.button-box {
  position: absolute;
  width: 60%;
  display: flex;
  justify-content: space-evenly;
}
.bottom-button {
  width: 15%;
  border-radius: 10px;
}
.messages-container {
  width: 100%;
  height: 90%;
  /* justify-content: space-between; */
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  box-sizing: border-box;
  overflow: auto;
}
.rightviduchat {
  align-self: flex-end;
  word-break: break-all;
  text-align: right;
}
.left {
  align-self: flex-start;
  word-break: break-all;
}
.words-container {
  background-color: white;
  border-radius: 10px;
  margin: 10px;
  padding: 10px;
}
</style>
