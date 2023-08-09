<template>
  <div id="main-container" class="container">
    <div id="join" v-if="!sessionCamera">
      <div id="join-dialog" class="jumbotron vertical-center">
        <h1>참여자 정보</h1>
        <video id="my-video" autoplay="true" :srcObject="myVideo"></video>
        <div class="form-group">
          <p>
            <label>학 생</label>
            <input
              v-model="myUserName"
              class="form-control"
              type="text"
              required
            />
          </p>
          <p>
            <label>강의실 번호</label>
            <input
              v-model="mySessionId"
              class="form-control"
              type="text"
              required
            />
          </p>
          <p class="text-center">
            <button class="btn btn-lg btn-success" @click="joinSession()">
              입장
            </button>
          </p>
        </div>
      </div>
    </div>

    <div id="session" v-if="sessionCamera">
      <div>
        <MetronomeApp />
      </div>
      <div id="session-header">
        <h1 id="session-title">{{ mySessionId }}</h1>
        <input
          class="btn btn-large btn-danger"
          type="button"
          id="buttonLeaveSession"
          @click="leaveSession"
          value="Leave session"
        />
      </div>

      <div class="livemeeting-container">
        <div class="aditional-function">
          <!-- <div id="main-video"> -->
          <user-video :stream-manager="mainStreamManager" />
          <!-- </div> -->
          <MetronomeApp />
          <TunerApp v-if="popState" @close="changePopState()" />
          <p @click="changePopState()">튜너</p>
        </div>

        <div id="video-container" class="video-box">
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
        </div>

        <div class="message-box">
          <div class="messages-container">
            <!-- Sent Messages -->
            <div class="sent-messages">
              <div
                v-for="(message, index) in receivedMessages"
                :class="message.className"
                :key="index"
              >
                <div>
                  {{ message.message }}
                </div>
              </div>
            </div>
          </div>

          <!-- Received Messages -->

          <div class="message-input-form">
            <!-- 메세지를 입력하는 input 요소 -->
            <input
              type="text"
              v-model="newMessage"
              @keyup.enter="sendMessage"
            />

            <!-- 메세지를 보내는 버튼 -->
            <button @click="sendMessage">전송</button>
          </div>
        </div>
        <button
          id="buttonScreenShare"
          @click="publishScreenShare"
          v-if="sessionScreen"
        >
          Start Screen Share
        </button>
      </div>

      <div id="main-video" class="col-md-6">
        <user-video :stream-manager="mainStreamManager" />
      </div>

      <div id="main-video" class="col-md-6">
        <p></p>
        <video autoplay playsinline="true"></video>
      </div>
      <div class="col-md-6">
        <div class="row panel panel-default">
          <div class="panel-heading">User Screens</div>
          <div class="panel-body" id="container-screens"></div>
        </div>
      </div>

      <div id="video-container" class="col-md-6">
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
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "../components/LiveMeeting/UserVideo";
import { useRoute } from "vue-router";
import MetronomeApp from "../components/LiveMeeting/MetronomeApp";
import TunerApp from "../components/LiveMeeting/TunerApp.vue";
axios.defaults.headers.post["Content-Type"] = "application/json";

const APPLICATION_SERVER_URL =
  process.env.NODE_ENV === "production"
    ? ""
    : "http://localhost:8080/api/lectures/";

export default {
  name: "App",

  components: {
    UserVideo,
    MetronomeApp,
    TunerApp,
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

      // 대기 화면 내 비디오 확인
      myVideo: null,
    };
  },
  created() {
    // App.vue가 생성되면 소켓 연결을 시도합니다.
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
            data: this.myUserName + ":" + this.newMessage, // Any string (optional)
            to: [], // Array of Connection objects (optional. Broadcast to everyone if empty)
            type: "my-chat",
            // The type of message (optional)
          })
          .then(() => {
            console.log("Message successfully sent");
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
    appendUserData(videoElement, connection) {
      var userData;
      var nodeId;
      if (typeof connection === "string") {
        userData = connection;
        nodeId = connection;
      } else {
        userData = JSON.parse(connection.data).clientData;
        nodeId = connection.connectionId;
      }
      var dataNode = document.createElement("div");
      dataNode.className = "data-node";
      dataNode.id = "data-" + nodeId;
      dataNode.innerHTML = "<p>" + userData + "</p>";
      videoElement.parentNode.insertBefore(dataNode, videoElement.nextSibling);
      this.addClickListener(videoElement, userData);
    },
    addClickListener(element, userData) {
      element.addEventListener("click", () => {
        const mainVideo = document.querySelector("#main-video video");
        if (mainVideo.srcObject !== element.srcObject) {
          const mainVideoContainer = document.querySelector("#main-video");
          mainVideoContainer.style.display = "none";

          const mainVideoText = mainVideoContainer.querySelector("p");
          mainVideoText.innerHTML = userData;

          mainVideo.srcObject = element.srcObject;
          mainVideoContainer.style.display = "block";
        }
      });
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
            console.log(
              'User pressed the "Stop sharing" button!!!!!!!!!!!!!!!!!'
            );
            this.sessionScreen.unpublish(publisherScreen);
            document.getElementById("buttonScreenShare").style.visibility =
              "visible";
            this.screensharing = false;
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
        if (event.stream.typeOfVideo == "CAMERA") {
          // Subscribe to the Stream to receive it. HTML video will be appended to element with 'container-cameras' id
          var subscriber = this.sessionCamera.subscribe(
            event.stream,
            "container-cameras"
          );
          // When the HTML video has been appended to DOM...
          subscriber.on("videoElementCreated", (event) => {
            // Add a new <p> element for the user's nickname just below its video
            this.appendUserData(event.element, subscriber.stream.connection);
          });
        }
      });
      this.sessionScreen.on("streamCreated", (event) => {
        if (event.stream.typeOfVideo == "SCREEN") {
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
        console.log(event.from);
        if (receivedMessage != this.myUserName + ":" + this.newMessage) {
          this.receivedMessages.push({
            message: receivedMessage,
            className: "left",
          });
        } else {
          this.receivedMessages.push({
            message: receivedMessage,
            className: "right",
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
            console.log(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
          });
      });
      this.getToken(this.mySessionId).then((tokenScreen) => {
        // Create a token for screen share
        this.sessionScreen
          .connect(tokenScreen, { clientData: this.myUserName })
          .then(() => {
            document.getElementById("buttonScreenShare").style.visibility =
              "visible";
            console.log("Session screen connected");
          })
          .catch((error) => {
            console.warn(
              "There was an error connecting to the session for screen share:",
              error.code,
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
      // Remove beforeunload listener
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
    console.log("mounted()가 호출 되었습니다:", this);
  },
};
</script>

<style>
#my-video {
  width: 300px;
  height: 300px;
}

.livemeeting-container {
  width: 100vw;
  height: 90vh;
  display: flex;
}
#local-video-undefined {
  display: flex;
  width: 100%;
  justify-content: center;
}
.split-screen {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.aditional-function {
  width: 16%;
  float: left;
  border: solid 1px red;
  box-sizing: border-box;
}
.video-box {
  width: 66%;
  border: solid 1px red;
  float: center;
}
.message-box {
  width: 16%;
  float: right;
  border: solid 1px red;
  box-sizing: border-box;
  position: relative;
}
.messages-container {
  display: flex;
  width: 100%;
  justify-content: space-between;
  box-sizing: border-box;
}
.sent-messages {
  width: 100%;
}
.message-input-form {
  position: absolute;
  bottom: 5px;
}
.tuner-container {
  display: flex;
  justify-content: center;
}
.right {
  display: flex;
  justify-content: right;
  align-content: center;
}
</style>
