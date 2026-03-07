document.addEventListener("DOMContentLoaded",loadAdminSidebar);

function loadAdminSidebar(){

    fetch("sidebar-admin.html")

        .then(res=>res.text())

        .then(data=>{

            document.getElementById("adminSidebar").innerHTML=data;

        });

}


function navigate(page){

    window.location.href=page+".html";

}

function logout(){

    window.location.href="../login.html";

}