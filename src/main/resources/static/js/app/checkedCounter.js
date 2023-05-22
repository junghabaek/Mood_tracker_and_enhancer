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

  if (checkedCount==2 || (selected_level==1 && checkedCount<2) || (selected_level==6 && checkedCount>2)){
  alert("Your daily mood is tracked.");
  }
  var jsonCheckedTasks = JSON.stringify(checkedTasks);

  var encodedList = encodeURIComponent(jsonCheckedTasks);

  var selected_level = selected_level;

  encodedList += "&selected_level=" + encodeURIComponent(selected_level);

  var redirectURL = "http://localhost:8080/level_selected/?task_list=" + encodedList;

  window.location.href = redirectURL;


}