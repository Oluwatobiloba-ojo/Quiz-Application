function sendData(){
    let firstName = document.getElementById('fname').value;
    let  lastName = document.getElementById('lname').value;
    let  dateOfBirth = document.getElementById('d_o_b').value;
    let  password = document.getElementById('password').value;
    let confirmPassword = document.getElementById('second_password').value;
    let email = document.getElementById('email').value;
    let role = document.getElementById('role').value;

    const obj = {
        firstName: firstName,
        lastName:  lastName,
        dateOfBirth : dateOfBirth,
        password : password,
        confirmPassword : confirmPassword,
        email: email,
        role: role
    }
    let url = "http://localhost:9090/api/v1/quiz_app/user";
    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(obj)
    })
        .then(response =>{
            if(!response.ok) {
                throw new Error(`Http Error Status ${response.status}`);
            }
            return response.json()
        })
        .then(data => console.log(data.body))
        .catch(error => console.log("Error: ", error.message));
}
