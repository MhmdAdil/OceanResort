document.addEventListener("DOMContentLoaded", function () {

    loadRoomIds();

    document.getElementById("roomId").addEventListener("change", function () {

        const roomId = this.value;

        if (roomId) {
            loadRoom(roomId);
        }

    });

    document.getElementById("editRoomForm").addEventListener("submit", updateRoom);

});


function loadRoomIds() {

    fetch("/OceanResort/getRooms")

        .then(response => response.json())

        .then(data => {

            const select = document.getElementById("roomId");

            select.innerHTML = "<option value=''>Select Room</option>";

            data.forEach(room => {

                const option = document.createElement("option");

                option.value = room.room_id;
                option.text = "Room " + room.room_id;

                select.appendChild(option);

            });

        })

        .catch(error => {

            console.error("Error loading room IDs:", error);

        });

}


function loadRoom(roomId) {

    fetch("/OceanResort/getRoom?id=" + roomId)

        .then(response => response.json())

        .then(room => {

            document.getElementById("roomNumber").value = room.room_code;
            document.getElementById("roomType").value = room.room_type;
            document.getElementById("price").value = room.price;
            document.getElementById("capacity").value = room.capacity;
            document.getElementById("status").value = room.status;

        });

}


function updateRoom(e) {

    e.preventDefault();

    const data = new URLSearchParams();

    data.append("roomId", document.getElementById("roomId").value);
    data.append("roomCode", document.getElementById("roomNumber").value);
    data.append("roomType", document.getElementById("roomType").value);
    data.append("price", document.getElementById("price").value);
    data.append("capacity", document.getElementById("capacity").value);
    data.append("status", document.getElementById("status").value);

    fetch("/OceanResort/updateRoom", {

        method: "POST",

        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },

        body: data.toString()

    })

        .then(response => response.text())

        .then(message => {

            alert(message);

            window.location.href = "room-details.html";

        });

}


function navigate(page) {

    if (page === "rooms") {
        window.location.href = "room-details.html";
    } else {
        window.location.href = page + ".html";
    }

}function navigate(page) {

    if (page === "rooms") {
        window.location.href = "room-details.html";
    } else {
        window.location.href = page + ".html";
    }

}


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

function logout() {

    window.location.href = "login.html";

}