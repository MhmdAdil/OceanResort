function register() {

    const staffName = document.getElementById("staffName").value;
    const phone = document.getElementById("phone").value;
    const email = document.getElementById("email").value;
    const address = document.getElementById("address").value;
    const age = document.getElementById("age").value;
    const role = document.getElementById("role").value;

    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    const error = document.getElementById("error");

    const data = new URLSearchParams();

    data.append("staffName", staffName);
    data.append("phone", phone);
    data.append("email", email);
    data.append("address", address);
    data.append("age", age);
    data.append("role", role);
    data.append("password", password);
    data.append("confirmPassword", confirmPassword);

    fetch("/OceanResort/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: data.toString()
    })
        .then(response => response.text())
        .then(result => {

            if(result === "success"){

                error.style.color = "green";
                error.innerText = "Staff registered successfully!";

                // ✅ CLEAR ALL FIELDS
                document.querySelector(".register-card").querySelectorAll("input").forEach(input => input.value = "");

                // reset role dropdown
                document.getElementById("role").selectedIndex = 0;

            }
            else if(result === "exists"){
                error.innerText = "Phone number already registered!";
            }
            else if(result === "mismatch"){
                error.innerText = "Passwords do not match!";
            }
            else if(result === "empty"){
                error.innerText = "All fields required!";
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