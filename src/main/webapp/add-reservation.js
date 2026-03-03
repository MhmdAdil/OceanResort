// Function to generate Guest ID
function generateGuestId() {
    const guestIdField = document.getElementById("guestId");

    // Generate random 4 digit number
    const newId = "G" + Math.floor(1000 + Math.random() * 9000);

    guestIdField.value = newId;
}

// Generate ID when page loads
document.addEventListener("DOMContentLoaded", function () {
    generateGuestId();
});

// Handle form submit
document.getElementById("reservationForm").addEventListener("submit", function (e) {
    e.preventDefault();

    const data = new URLSearchParams();

    data.append("guestName", document.getElementById("guestName").value);
    data.append("phone", document.getElementById("phone").value);
    data.append("email", document.getElementById("email").value);
    data.append("nic", document.getElementById("nic").value);
    data.append("address", document.getElementById("address").value);
    data.append("roomType", document.getElementById("roomType").value);
    data.append("guestCount", document.getElementById("guestCount").value);
    data.append("checkIn", document.getElementById("checkIn").value);
    data.append("checkOut", document.getElementById("checkOut").value);
    data.append("specialNote", document.getElementById("specialNote").value);

    fetch("/OceanResort/addReservation", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: data.toString()
    })
        .then(response => response.text())
        .then(result => {

            if (result === "success") {

                alert("Reservation saved successfully!");

                // Reset form
                document.getElementById("reservationForm").reset();

                // 🔥 Generate new Guest ID after reset
                generateGuestId();
            }
            else if (result === "empty") {
                alert("Please fill all required fields.");
            }
            else {
                alert("Server error.");
            }
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Error connecting to server.");
        });
});