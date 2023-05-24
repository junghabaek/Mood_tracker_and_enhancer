var main = {
  init: function() {
    var _this = this;

    document.getElementById('btn-update').addEventListener('click', function() {
      _this.update();
    });

    document.getElementById('btn-delete').addEventListener('click', function() {
      _this.delete();
    });
  },

  update: function() {
    console.log("update");
    var data = {
      task_name: document.getElementById('task_name').value,
      task_level: document.getElementById('task_level').value
    };

    var path = window.location.pathname;
    var id = path.split('/').pop();

    var xhr = new XMLHttpRequest();
    xhr.open('PUT', '/v1/PUT/task/' + id, true);
    xhr.setRequestHeader('Content-Type', 'application/json; charset=utf-8');
    xhr.onload = function() {
      if (xhr.status === 200) {
        alert('Update successful');
        window.location.href = '/v1/task_manager';
      } else {
        alert('Error: ' + xhr.status);
      }
    };
    xhr.onerror = function() {
      alert('Request failed');
    };
    xhr.send(JSON.stringify(data));
  },

  delete: function() {
    var path = window.location.pathname;
    var id = path.split('/').pop();

    var xhr = new XMLHttpRequest();
    xhr.open('DELETE', '/v1/DELETE/task/' + id, true);
    xhr.setRequestHeader('Content-Type', 'application/json; charset=utf-8');
    xhr.onload = function() {
      if (xhr.status === 200) {
        alert('Task deleted');
        window.location.href = '/v1/task_manager';
      } else {
        alert('Error: ' + xhr.status);
      }
    };
    xhr.onerror = function() {
      alert('Request failed');
    };
    xhr.send();
  }
};

main.init();
