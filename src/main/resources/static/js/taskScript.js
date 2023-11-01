let taskName = document.getElementById("name");
let taskDetails = document.getElementById("details");
let taskId = document.getElementById("id").value;
let taskStatus = document.getElementById("status")
const status = (taskStatus.value !== "Uncompleted").toString()

document.getElementById("update").addEventListener("click", async () => {
    let data = {
        name: taskName.value,
        details: taskDetails.value,
        status
    }

    const res = await fetch("api/tasks/" + taskId, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
    if(res.ok) {
        window.location.replace("/")
    }
    else {
        alert("error")
    }
})

document.getElementById("delete").addEventListener("click", async () => {
    const res = await fetch("api/tasks/" + taskId, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        },
    })
    if(res.ok) {
        window.location.replace("/")
    }
    else {
        alert("error")
    }
})

