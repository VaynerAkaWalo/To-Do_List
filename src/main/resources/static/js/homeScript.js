let changeStatusButtons = document.getElementsByClassName("changeStatus")
let tasks = document.getElementsByClassName("task")
let showCompleted = document.getElementById("showCompleted")

for (const changeStatusButton of changeStatusButtons) {
    changeStatusButton.addEventListener("click", () => {
        fetch("/api/task/" + changeStatusButton.getAttribute('taskid'), {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json"
            },
        }).then(window.location.reload.bind(window.location))
    })
}

function filterTasks() {
    if (showCompleted.checked === true) {
        for (const task of tasks) {
            task.style.display = "flex"
        }
    }
    else {
        for (const task of tasks) {
            if (task.getAttribute("status") === "Completed") {
                task.style.display = "none"
            }
            else {
                task.style.display = "flex"
            }
        }
    }
}

filterTasks()
showCompleted.addEventListener("change", () => {
    filterTasks();
})
