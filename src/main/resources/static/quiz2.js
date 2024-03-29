// let body = document.getElementsByClassName("header")
// console.log(body);

let container = document.getElementById("header")

const getQuestion = async(url)=> {
    try {
        const response = await fetch(url, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            }
        })
        let result = await response.json();
        return result.message;
    } catch (error) {
        console.error("Error -> ", error.message);
    }
}

container.addEventListener('click', (event) => {
    event.preventDefault()
    let url = "http://localhost:9090/api/v1/quiz/question/"
    if (event.target.textContent === "Take quiz"){
        let title = event.target.parentElement.getElementsByTagName("h3")[0].textContent;
        url += title;
        getQuestion(url).then(result => {
           displayQuestion(result);
        });
    }
})

const displayQuestion = (obj) =>{
    let newPage = window.open();
    for (const resultKey in obj) {
        let question = obj[resultKey];
        let questionFormat = document.createElement("h4");
        questionFormat.textContent = question["question"]
        let optionA = document.createElement("input")
        optionA.type = "checkbox"
        optionA.value = question["optionA"];
        let optionALabel = document.createElement("label")
        optionALabel.textContent = question["optionA"]
        optionALabel.appendChild(optionA)
        let optionB = document.createElement("input")
        optionB.type = "checkbox"
        optionB.value = question["optionB"]
        let optionBLabel = document.createElement("label")
        optionBLabel.textContent = question["optionB"]
        optionBLabel.appendChild(optionB)
        let optionC = document.createElement("input")
        optionC.type = "checkbox"
        optionC.value = question["optionC"]
        let optionCLabel = document.createElement("label")
        optionCLabel.textContent = question["optionC"]
        let optionD = document.createElement("input")
        optionD.type = "checkbox"
        optionD.value = question["optionD"]
        let optionDLabel = document.createElement("label")
        optionDLabel.textContent = question["optionD"]
        let div = document.createElement("div")
        div.appendChild(questionFormat);
        div.appendChild(optionA)
        div.appendChild(optionALabel)
        div.appendChild(document.createElement("br"))
        div.appendChild(optionB)
        div.appendChild(optionBLabel)
        div.appendChild(document.createElement("br"))
        div.appendChild(optionC)
        div.appendChild(optionCLabel)
        div.appendChild(document.createElement("br"))
        div.appendChild(optionD)
        div.appendChild(optionDLabel)
        div.appendChild(document.createElement("br"))
        newPage.document.body.appendChild(div);
    }
}

