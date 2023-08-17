import axios from "@/api/axios";

async function apiGetChatLog(id) {
  // console.log("apiGetChatLog id : ", id);

  const response = await axios.get(`/chats/${id}`);

  // console.log(response);
  if (response.status == 200) {
    // console.log("apiGetChatLog Data : ", response.data);
    return response.data;
  } else {
    alert("api/chats.js apiGetChatLog 중 에러 발생!!!!!!");
    return null;
  }
}

function apiChatcreate(context, data, id) {
  axios
    .post(`/chats/${id}`, JSON.stringify(data))
    .then(() => {
      // console.log("채팅 데이터 저장 완료");
    })
    .catch((error) => {
      console.error("POST 요청 에러 : ", error);
    });
}

async function apiGetChatParticipant(contactId) {
  // console.log("apiGetChatParticipant contactId : ", contactId);

  const response = await axios.get(`/chats/${contactId}/profile`);
  try {
    // console.log(response);
    if (response.status == 200) {
      // console.log("apiGetChatParticipant Data : ", response.data);
      return response.data;
    } else {
      alert("api/chats.js apiGetChatParticipant 중 에러 발생!!!!!!");
      return null;
    }
  } catch (error) {
    console.log(error);
  }
}

export { apiGetChatLog, apiChatcreate, apiGetChatParticipant };
