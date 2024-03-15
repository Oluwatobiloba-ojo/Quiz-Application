let userEmail = document.getElementById("userEmail");
function displayTitle(){
    let url = "http://localhost:9090/api/v1/quiz_app/quiz/"+userEmail.value;
    fetch(url, {
        method: "GET",
        headers:{
            "Content-Type": "application/json"
        }
    }).then(response => response.json())
      .then(data=>{
          if (data.successful === true) {
              display(data.message.message);
          }
      }).catch(error => console.error(`Error is fetching data `, error))
}

function createPage(obj, container, newPage) {
    for (let item of obj) {
        let subContainer = document.createElement("div")
        subContainer.className = "subContainer";
        let quizTitle = document.createElement("h3")
        quizTitle.className = "title"
        let quizDescription = document.createElement("p")
        let submit = document.createElement("button")
        submit.textContent = "Take quiz"
        quizTitle.textContent = item.title;
        quizDescription.textContent = item.description
        subContainer.appendChild(quizTitle)
        subContainer.appendChild(quizDescription)
        subContainer.appendChild(submit)
        container.appendChild(subContainer);
        newPage.document.body.appendChild(container);
    }
}

function display(obj){
    let newPage = window.open()
    let cssLink = document.createElement("link")
    let scriptLink = document.createElement("script")
    cssLink.href = "page.css";
    cssLink.rel = "styleSheet";
    scriptLink.src = "quiz2.js";
    newPage.document.head.appendChild(cssLink);
    let container = document.createElement("div");
    container.id = "header";
    createPage(obj, container, newPage);
    newPage.document.body.appendChild(scriptLink)
}
