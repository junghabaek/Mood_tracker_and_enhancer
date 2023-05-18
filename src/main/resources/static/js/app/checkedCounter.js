function countChecked(selected_level) {

  var checkboxes = document.querySelectorAll('input[type="checkbox"]');

  var checkedCount = 0;

  var checkedTasks = [];

  checkboxes.forEach(function(checkbox) {
    if (checkbox.checked) {
      checkedCount++;
      checkedTasks.push(checkbox.id);
    }
  });

  // Display the count
  alert("You have completed " + checkedCount +" tasks");

  var jsonCheckedTasks = JSON.stringify(checkedTasks);

  var encodedList = encodeURIComponent(jsonCheckedTasks);

  var selected_level = selected_level;

  encodedList += "&selected_level=" + encodeURIComponent(selected_level);

  var redirectURL = "http://localhost:8080/level_selected/?task_list=" + encodedList;

  window.location.href = redirectURL;


}
