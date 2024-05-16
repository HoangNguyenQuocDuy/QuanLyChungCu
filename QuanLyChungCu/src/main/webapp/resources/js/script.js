function deleteRoom(url) {
    if (confirm("Do you want to delete this room?") === true) {
        fetch(url, {
            method: "delete"
        }).then(res => {
            console.info(res);
            if (res.status === 204)
                location.reload();
            else {
                res.text().then(html => {
                    const regex = /<b>Message<\/b>\s*(.*?)<p>/;
                    const match = html.match(regex);
                    if (match && match.length > 1) {
                        const errorMessage = match[1].substring(0, match[1].indexOf("</p>"));
                        alert("Error: " + errorMessage);
                    } else {
                        alert("Error: Unknown error occurred.");
                    }
                });
            }
        }).catch(err => {
            alert("Fetch Error: " + err);
        });
    }
}

function blockUser(url) {
    if (confirm("Do you want to block this user?") === true) {
        fetch(url, {
            method: "post"
        }).then(res => {
            console.info(res);
            if (res.status === 200)
                location.reload();
            else {
                res.text().then(html => {
                    const regex = /<b>Message<\/b>\s*(.*?)<p>/;
                    const match = html.match(regex);
                    if (match && match.length > 1) {
                        const errorMessage = match[1].substring(0, match[1].indexOf("</p>"));
                        alert("Error: " + errorMessage);
                    } else {
                        alert("Error: Unknown error occurred.");
                    }
                });
            }
        }).catch(err => {
            alert("Fetch Error: " + err);
        });
    }
}

function addLocker(url) {
    if (confirm("Do you want add new locker?") === true) {
        fetch(url, {
            method: "post"
        }).then(res => {
            console.info(res);
            if (res.status === 201)
                location.reload();
            else {
                res.text().then(html => {
                    const regex = /<b>Message<\/b>\s*(.*?)<p>/;
                    const match = html.match(regex);
                    if (match && match.length > 1) {
                        const errorMessage = match[1].substring(0, match[1].indexOf("</p>"));
                        alert("Error: " + errorMessage);
                    } else {
                        alert("Error: Unknown error occurred.");
                    }
                });
            }
        }).catch(err => {
            alert("Fetch Error: " + err);
        });
    }
}

function deleteLocker(url) {
    if (confirm("Do you want to delete this locker?") === true) {
        fetch(url, {
            method: "delete"
        }).then(res => {
            console.info(res);
            if (res.status === 204)
                location.reload();
            else {
                res.text().then(html => {
                    const regex = /<b>Message<\/b>\s*(.*?)<p>/;
                    const match = html.match(regex);
                    if (match && match.length > 1) {
                        const errorMessage = match[1].substring(0, match[1].indexOf("</p>"));
                        alert("Error: " + errorMessage);
                    } else {
                        alert("Error: Unknown error occurred.");
                    }
                });
            }
        }).catch(err => {
            alert("Fetch Error: " + err);
        });
    }
}

function deleteOrder(url) {
    if (confirm("Do you want to delete this order?") === true) {
        fetch(url, {
            method: "delete"
        }).then(res => {
            console.info(res);
            if (res.status === 204)
                location.reload();
            else {
                res.text().then(html => {
                    const regex = /<b>Message<\/b>\s*(.*?)<p>/;
                    const match = html.match(regex);
                    if (match && match.length > 1) {
                        const errorMessage = match[1].substring(0, match[1].indexOf("</p>"));
                        alert("Error: " + errorMessage);
                    } else {
                        alert("Error: Unknown error occurred.");
                    }
                });
            }
        }).catch(err => {
            alert("Fetch Error: " + err);
        });
    }
}

function confirmOrder(url) {
    if (confirm("Do you want to confirm this order?") === true) {
        fetch(url, {
            method: "post"
        }).then(res => {
            console.info(res);
            if (res.status === 200)
                location.reload();
            else {
                res.text().then(html => {
                    const regex = /<b>Message<\/b>\s*(.*?)<p>/;
                    const match = html.match(regex);
                    if (match && match.length > 1) {
                        const errorMessage = match[1].substring(0, match[1].indexOf("</p>"));
                        alert("Error: " + errorMessage);
                    } else {
                        alert("Error: Unknown error occurred.");
                    }
                });
            }
        }).catch(err => {
            alert("Fetch Error: " + err);
        });
    }
}

function updateEntryRight(url, status) {
    let actionText = status === 'Confirmed' ? 'confirm' : 'cancel';

    if (confirm(`Do you want to ${actionText} this entry right?`) === true) {
        fetch(url, {
            method: "put",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({status: status})
        }).then(res => {
            console.info(res);
            if (res.status === 200)
                location.reload();
            else {
                res.text().then(html => {
                    const regex = /<b>Message<\/b>\s*(.*?)<p>/;
                    const match = html.match(regex);
                    if (match && match.length > 1) {
                        const errorMessage = match[1].substring(0, match[1].indexOf("</p>"));
                        alert("Error: " + errorMessage);
                    } else {
                        alert("Error: Unknown error occurred.");
                    }
                });
            }
        }).catch(err => {
            alert("Fetch Error: " + err);
        });
    }
}

function updateParkingRight(url, status) {
    let actionText = status === 'Confirmed' ? 'confirm' : 'cancel';

    if (confirm(`Do you want to ${actionText} this parking right?`) === true) {
        fetch(url, {
            method: "put",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({status: status})
        }).then(res => {
            console.info(res);
            if (res.status === 200)
                location.reload();
            else {
                res.text().then(html => {
                    const regex = /<b>Message<\/b>\s*(.*?)<p>/;
                    const match = html.match(regex);
                    if (match && match.length > 1) {
                        const errorMessage = match[1].substring(0, match[1].indexOf("</p>"));
                        alert("Error: " + errorMessage);
                    } else {
                        alert("Error: Unknown error occurred.");
                    }
                });
            }
        }).catch(err => {
            alert("Fetch Error: " + err);
        });
    }
}