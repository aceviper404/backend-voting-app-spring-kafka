<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Realtime Data</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="/webjars/bootstrap/5.1.1/css/bootstrap.min.css">
    <script>
        function updateTable() {
            $.ajax({
                url: "/getCandidates", // Controller mapping to get candidates data
                method: "GET",
                dataType: "json",
                success: function(data) {
                    var tableHtml = "<table class='table table-striped table-hover table-bordered text-center'><tr><th>Name</th><th>Number of Votes</th></tr>";
                    $.each(data, function(index, candidate) {
                        tableHtml += "<tr><td>" + candidate.name + "</td><td>" + candidate.numberOfVotes + "</td></tr>";
                    });
                    tableHtml += "</table>";
                    $("#candidateTable").html(tableHtml);
                }
            });
        }
        $(document).ready(function() {
            updateTable();
            setInterval(updateTable, 5000); // Update every 5 seconds
        });
    </script>
</head>
<body>
    <div id="candidateTable"></div>
</body>
</html>