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
  }
}

async function apiFindId(params) {
  try {
    const response = await axios.post('/auth/find-id', JSON.stringify(params));
    return response;
  } catch (error) {
    console.error(error.response.data);
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

async function apiCheckId(params) {
  const url = `/auth/check-id/?id=${params}`;
  console.log("URL:", url); 
  try {
    const response =await axios.get(`/auth/check-id/?id=${params}`);
    console.log(response);

    return response;
  } catch (error) {
    console.error(error.response.data)
  }
}

async function apiRegister(params) {
  try {
    const response = await axios.post("/auth/sign-up", params);
    
    return response;
  } catch (error) {
    console.error(error.response.data);
  }
}




export { apiLogin, apiCheckId, apiFindId, apiFindPw, apiRegister}