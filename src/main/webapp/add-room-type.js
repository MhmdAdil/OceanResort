function generateRoomTypeId() {
    const id = "RT" + Math.floor(1000 + Math.random() * 9000);
    document.getElementById("roomTypeId").value = id;
}

document.addEventListener("DOMContentLoaded", () => {
    generateRoomTypeId();
});

document.getElementById("roomTypeForm").addEventListener("submit", function(e){

    e.preventDefault();

    const data = new URLSearchParams();

    data.append("roomTypeId", document.getElementById("roomTypeId").value);
    data.append("name", document.getElementById("name").value);
    data.append("price", document.getElementById("price").value);
    data.append("maxGuests", document.getElementById("maxGuests").value);
    data.append("description", document.getElementById("description").value);

    fetch("addRoomType", {
        method:"POST",
        headers:{
            "Content-Type":"application/x-www-form-urlencoded"
        },
        body:data.toString()
    })
        .then(res=>res.text())
        .then(result=>{

            if(result === "success"){
                alert("Room Type saved successfully!");

                document.getElementById("roomTypeForm").reset();
                generateRoomTypeId();
            }
            else{
                alert("Server error");
            }

        })
        .catch(err=>{
            console.log(err);
            alert("Connection error");
        });

});