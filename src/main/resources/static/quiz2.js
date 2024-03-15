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
        return result.message.message;
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
            for (const resultKey in result) {
                let question = result[resultKey];
                for (const questionKey in question) {
                    if(questionKey !== "id"){
                        console.log(questionKey);
                    }
                }
                console.log("  ")
            }
        });
    }
})

