function login() {

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    const error = document.getElementById("error");

    const data = new URLSearchParams();
    data.append("username", username);
    data.append("password", password);

    fetch("/OceanResort/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: data.toString()
    })

        .then(res => res.text())
        .then(result => {

            if(result === "success"){
                window.location.href = "dashboard.html";
            }
            else if(result === "invalid"){
                error.innerText = "Invalid username or password";
            }
            else if(result === "empty"){
                error.innerText = "All fields required";
            }
            else{
                error.innerText = "Server error. Try again.";
            }

        })

        .catch(err=>{
            console.log(err);
            error.innerText="Connection error.";
        });
}