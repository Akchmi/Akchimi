// import axios from "@/api/axios";

// function apiLogin(params, success, fail) {
//   axios.post(`/auth/login`,
//     JSON.stringify(params)
//   )
//   .then(success)
//   .catch(fail)
// }


// function apiFindId(params, success, fail) {
//   axios.post('/auth/find-id',
//     JSON.stringify(params)
//   )
//   .then(success)
//   .catch(fail)

// }




// function apiCheckId(loginId, success, fail) {
//   axios.get(`/auth/check-id/?id=${loginId}`)
//   .than(success)
//   .catch(fail)
// }




// export { apiLogin, apiCheckId, apiFindId }

import axios from "@/api/axios";

async function apiLogin(params) {
  try {
    const response = await axios.post(`/auth/login`, JSON.stringify(params));
    return response;
  } catch (error) {
    console.error(error.response.data);
    throw error;
  }
}

async function apiFindId(params) {
  try {
    const response = await axios.post('/auth/find-id', JSON.stringify(params));
    return response;
  } catch (error) {
    console.error(error.response.data);
    throw error;
  }
}

async function apiFindPw(params) {
  try {
    const response = await axios.put('/auth/temporary-password', JSON.stringify(params));
    return response;  
  } catch (error) {
    console.error(error.response.data)

  }
}


function apiCheckId(loginId, success, fail) {
  axios.get(`/auth/check-id/?id=${loginId}`)
  .than(success)
  .catch(fail)
}

// function apiRegister() {
//   axios.post(`/auth/sign-up`)

// }


export { apiLogin, apiCheckId, apiFindId, apiFindPw }