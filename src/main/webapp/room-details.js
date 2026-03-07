document.addEventListener("DOMContentLoaded", loadRooms);

function loadRooms(){

    fetch("/OceanResort/getRooms")

        .then(res => res.json())

        .then(data => {

            const grid = document.getElementById("roomGrid");

            grid.innerHTML = "";

            data.forEach(room => {

                const card = document.createElement("div");

                card.className = "room-card";

                let statusClass = "available";

                if(room.status === "Occupied") statusClass = "occupied";
                if(room.status === "Maintenance") statusClass = "maintenance";

                card.innerHTML = `

<div class="room-header">
<span class="room-id">${room.room_code}</span>
<span class="status ${statusClass}">${room.status}</span>
</div>

<h3>${room.room_type}</h3>

<div class="room-info">
<p><span class="material-symbols-outlined">group</span> Capacity: ${room.capacity}</p>
<p><span class="material-symbols-outlined">payments</span> LKR ${room.price}</p>
</div>

<button class="edit-btn" onclick="editRoom(${room.room_id})">
<span class="material-symbols-outlined">edit</span>
Edit
</button>

`;

                grid.appendChild(card);

            });

        });

}

function editRoom(id){

    window.location.href = "edit-room.html?id=" + id;

}

function navigateToAddRoom(){
    window.location.href = "add-room.html";
}

function navigateToAddRoomType(){
    window.location.href = "add-room-type.html";
}