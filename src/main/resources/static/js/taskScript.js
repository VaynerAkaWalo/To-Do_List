let taskName = document.getElementById("name");
let taskDetails = document.getElementById("details");
let taskId = document.getElementById("id").value;

document.getElementById("update").addEventListener("click", () => {
    let data = {
        name: taskName.value,
        details: taskDetails.value
    }

    fetch("/task?id=" + taskId, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    }).then((res) => {
        if (res.ok) {
            alert("Task updated")
        }
    })
})

document.getElementById("delete").addEventListener("click", () => {
    fetch("/task?id=" + taskId, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        },
    }).then((res) => {
        if (res.ok) {
            window.location.replace("/")
        }
    })
})

