import axios from "@/api/axios";

function apiLogin(params, success, fail) {
  axios.post(`/auth/login`,
    JSON.stringify(params)
  )
  .then(success)
  .catch(fail)
}

function apiCheckId(loginId, success, fail) {
  axios.get(`/auth/check-id/?id=${loginId}`)
  .than(success)
  .catch(fail)
}


// function apiRegister() {
//   axios.post(`/auth/sign-up`)

// }


export { apiLogin, apiCheckId }