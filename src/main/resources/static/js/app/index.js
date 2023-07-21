fetch('/getSessionUser')
    .then(response => response.json())
    .then(data => {
        const sessionUser = data.sessionUser;
        console.log(sessionUser);
        const containerDiv = document.createElement('div');
        if (sessionUser === null || typeof sessionUser === 'undefined') {
            // If sessionUser does not exist
            containerDiv.innerHTML = `
                <button onclick="window.location.href='/oauth2/authorization/google';" type="button" class="btn btn-primary btn-lg px-4 gap-3">Google Login</button>
                <button onclick="window.location.href='/oauth2/authorization/naver';" type="button" class="btn btn-success btn-lg px-4">Naver Login</button>
            `;
        } else {
            // If sessionUser exists
            containerDiv.innerHTML = `
                <div>
                    <div class="alert alert-success gap-3" role="alert">
                        Hi ${sessionUser}, kindly check your mood for yourself.
                    </div>
                    <div class="gap-3">
                        <button onclick="window.location.href='/v1/mood_tracker';" type="button" class="btn btn-success btn-lg px-4">Track your Mood</button>
                        <button onclick="window.location.href='/v1/mood_history';" type="button" class="btn btn-outline-success btn-lg px-4">Mood history</button>
                        <button onclick="window.location.href='/v1/task_manager';" type="button" class="btn btn-outline-warning btn-lg px-4">Task Manager</button>
                    </div>
                    <p></p>
                    <button onclick="window.location.href='/logout';" type="button" class="btn btn-secondary btn-lg px-4">Logout</button>
                </div>
            `;
        }
        const mainDiv = document.querySelector('.px-4.py-5.my-5.text-center');
        mainDiv.appendChild(containerDiv);
    })
    .catch(error => {
        // Handle the error
        // console.log('Error:', error);
    });
