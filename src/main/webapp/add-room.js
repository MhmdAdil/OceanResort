// Function to generate Room ID
function generateRoomId() {
    const roomIdField = document.getElementById("roomId");
    const randomId = "RM" + Math.floor(100 + Math.random() * 900);
    roomIdField.value = randomId;
}

// Generate ID on page load
document.addEventListener("DOMContentLoaded", () => {
    generateRoomId();
});

// Form submit
document.getElementById("addRoomForm").addEventListener("submit", function (e) {
    e.preventDefault();

    const data = new URLSearchParams();

    data.append("roomCode", document.getElementById("roomId").value);
    data.append("roomType", document.getElementById("roomType").value);
    data.append("capacity", document.getElementById("capacity").value);
    data.append("price", document.getElementById("price").value);
    data.append("status", document.getElementById("status").value);
    data.append("description", document.getElementById("description").value);

    fetch("/OceanResort/addRoom", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: data.toString()
    })
        .then(response => response.text())
        .then(result => {
            if (result === "success") {
                alert("Room added successfully!");
                document.getElementById("addRoomForm").reset();
                generateRoomId(); // 🔥 Generate new ID after reset
            } else if (result === "empty") {
                alert("Please fill all required fields.");
            } else {
                alert("Server error.");
            }
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Error connecting to server.");
        });
});

document.addEventListener("DOMContentLoaded", function () {

    loadRoomTypes();

});


function loadRoomTypes(){

    fetch("/OceanResort/getRoomTypes")

        .then(response => {

            if(!response.ok){
                throw new Error("Server error");
            }

            return response.json();

        })

        .then(data => {

            const dropdown = document.getElementById("roomType");

            dropdown.innerHTML = "<option value=''>Select Room Type</option>";

            data.forEach(type => {

                const option = document.createElement("option");

                option.value = type.name;
                option.textContent = type.name;

                dropdown.appendChild(option);
            });
        })

        .catch(error => {

            console.error("Error loading room types:", error);

        });

}