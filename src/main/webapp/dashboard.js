function navigate(page){

  window.location.href = page + ".html";

}

function logout(){

  if(confirm("Are you sure you want to logout?")){

    window.location.href="login.html";

  }

}


/* OPEN MODAL */

const staffImage=document.getElementById("staffImage");

const modal=document.getElementById("profileModal");

const closeBtn=document.getElementById("closeProfile");

staffImage.onclick=function(){

  modal.style.display="flex";

}

closeBtn.onclick=function(){

  modal.style.display="none";

}


/* LOAD STAFF DATA */

window.addEventListener("DOMContentLoaded",loadProfile);


function loadProfile(){

  fetch("/OceanResort/getStaffProfile")

      .then(res=>res.json())

      .then(data=>{

        document.getElementById("staffId").value=data.id;

        document.getElementById("staffName").value=data.name;

        document.getElementById("staffEmail").value=data.email;

        document.getElementById("staffPhone").value=data.phone;

        document.getElementById("staffAddress").value=data.address;

        document.getElementById("staffType").value=data.role;

        document.getElementById("staffAge").value=data.age;

        document.getElementById("staffHeading").innerText=data.name;

        document.getElementById("staffTopName").innerText=data.name;

      })

      .catch(err=>console.log(err));

}



const upload = document.getElementById("profileUpload");

upload.addEventListener("change", function(){

  const file = this.files[0];

  if(!file) return;

  const formData = new FormData();
  formData.append("profileImage", file);

  fetch("/OceanResort/uploadProfileImage",{
    method:"POST",
    body:formData
  })
      .then(res=>res.text())
      .then(result=>{

        if(result==="success"){

          const reader = new FileReader();

          reader.onload = function(e){

            document.getElementById("profilePopupImage").src = e.target.result;
            document.getElementById("staffImage").src = e.target.result;

          };

          reader.readAsDataURL(file);

        }

      })
      .catch(err=>console.log(err));

});

function loadProfileImage(){

  fetch("/OceanResort/getProfileImage")
      .then(res=>res.blob())
      .then(blob=>{

        if(blob.size>0){

          const url = URL.createObjectURL(blob);

          document.getElementById("profilePopupImage").src = url;
          document.getElementById("staffImage").src = url;

        }

      });

}

window.addEventListener("DOMContentLoaded",loadProfileImage);


window.onload=function(){

  const saved=localStorage.getItem("staffImage");

  if(saved){

    document.getElementById("staffImage").src=saved;

    document.getElementById("profilePopupImage").src=saved;

  }

}