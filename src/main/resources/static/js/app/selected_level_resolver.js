function selected_level_resolver(event){
    event.preventDefault();
    let selectedOption = document.getElementById("dropdown").value;

//값을 API로 보내서 새로운 값을 받아서 새로운 페이지로 안내하는 js code임
//restfulAPI에서는 모든 뷰에관한건 js가 해결한다고함

    fetch("/v1/selected_level_resolver?value=" + selectedOption, {
        method: "GET"
    })
    .then(response => response.json())
    .then(responseData => {

    })
    .catch(error => {
        alert("error")
    });
};