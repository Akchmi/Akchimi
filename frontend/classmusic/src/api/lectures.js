import axios from "@/api/axios";

function apiGetLectureList(context, params){
    axios
    .get("/contacts", {
        params: params,
    }).then(({ data }) => {
        context.commit("SET_LECTURE_LIST", data);
    }).catch((error)=>{
        console.log("GET 요청 에러 : ", error)
    })
}

function apiPostLecture(context, params){
    axios
    .post("/contacts",{
        params: params
    }).then(({data}) =>{
        context.commit("POST_LECTURE", data)
    }).catch((error)=>{
        
    })
}

export {apiGetLectureList};