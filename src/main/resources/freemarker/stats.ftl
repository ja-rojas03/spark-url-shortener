<!DOCTYPE html>
<html lang="en">
<#include 'head.ftl'>
<body>
<#include 'navbar.ftl'>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <br><br>
            <h1 class="my-4">Statistics📊</h1>
            <div class="row">
                <div class="col-md-6" id="donut_values_browser"></div>
                <div class="col-md-6" id="column_values_day"></div>
            </div>
            <div class="row">
                <div class="col-md-6" id="bar_values_hour" style="margin: auto; display: block"></div>
                <div class="col-md-6" id="donut_values_os" style="margin: auto; display: block"></div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    // Load the Visualization API and the corechart package.
    google.charts.load('current', {'packages':['corechart']});

    // Set a callback to run when the Google Visualization API is loaded.
    google.charts.setOnLoadCallback(drawChart);
    function drawChart() {
        var data = google.visualization.arrayToDataTable([
            ['Webpage', 'Visits'],
            ['Chrome', ${chromeVisits}],
            ['Firefox', ${firefoxVisits}],
            ['Opera', ${operaVisits}],
            ['Edge', ${edgeVisits}],
            ['Safari', ${safariVisits}]
        ]);
        var options = {
            title: "Visits by webpage",
            width: 650,
            height: 450,
            pieHole: 0.4,
            legend: { position: "none" },
        };
        var chart = new google.visualization.PieChart(document.getElementById("donut_values_browser"));
        chart.draw(data, options);
    }
</script>

<script type="text/javascript">
    // Load the Visualization API and the corechart package.
    google.charts.load('current', {'packages':['corechart']});

    // Set a callback to run when the Google Visualization API is loaded.
    google.charts.setOnLoadCallback(drawChart);
    function drawChart() {
        var data = google.visualization.arrayToDataTable([
            ['Days', 'Visits', { role: 'style' } ],
            ['Monday', ${mondayVisits}, 'color: #1DA462'],
            ['Tuesday', ${tuesdayVisits}, 'color: #FF6611'],
            ['Wednesday', ${wednesdayVisits}, 'color: #B20317'],
            ['Thursday', ${thursdayVisits}, 'color: blue'],
            ['Friday', ${fridayVisits}, 'color: #00D3F9'],
            ['Saturday', ${saturdayVisits}, 'color: #00D3F9'],
            ['Sunday', ${sundayVisits}, 'color: #00D3F9']
        ]);
        var options = {
            title: "Visits by Day",
            width: 650,
            height: 450,
            bar: {groupWidth: "75%"},
            legend: { position: "none" },
        };
        var chart = new google.visualization.ColumnChart(document.getElementById("column_values_day"));
        chart.draw(data, options);
    }
</script>

<script type="text/javascript">
    // Load the Visualization API and the corechart package.
    google.charts.load('current', {'packages':['corechart']});

    // Set a callback to run when the Google Visualization API is loaded.
    google.charts.setOnLoadCallback(drawChart);
    function drawChart() {
        var data = google.visualization.arrayToDataTable([
            ['Hours', 'Visits', { role: "style" }],
            ['midnight', ${zero}, 'color: blue'],
            ['1', ${one}, 'color: blue'],
            ['2', ${two}, 'color: blue'],
            ['3', ${three}, 'color: blue'],
            ['4', ${four}, 'color: blue'],
            ['5', ${five}, 'color: blue'],
            ['6', ${six}, 'color: blue'],
            ['7', ${seven}, 'color: blue'],
            ['8', ${eight}, 'color: blue'],
            ['9', ${nine}, 'color: blue'],
            ['10', ${ten}, 'color: blue'],
            ['11', ${eleven}, 'color: blue'],
            ['noon', ${twelve}, 'color: blue'],
            ['13', ${thirteen}, 'color: blue'],
            ['14', ${fourteen}, 'color: blue'],
            ['15', ${fifteen}, 'color: blue'],
            ['16', ${sixteen}, 'color: blue'],
            ['17', ${seventeen}, 'color: blue'],
            ['18', ${eighteen}, 'color: blue'],
            ['19', ${nineteen}, 'color: blue'],
            ['20', ${twenty}, 'color: blue'],
            ['21', ${twenty_one}, 'color: blue'],
            ['22', ${twenty_two}, 'color: blue'],
            ['23', ${twenty_three}, 'color: blue']
        ]);
        var options = {
            title: "Visits by Hour",
            width: 650,
            height: 450,
            bar: {groupWidth: "50%"},
            legend: { position: "none" },
        };
        var chart = new google.visualization.BarChart(document.getElementById("bar_values_hour"));
        chart.draw(data, options);
    }
</script>

<script type="text/javascript">
    // Load the Visualization API and the corechart package.
    google.charts.load('current', {'packages':['corechart']});

    // Set a callback to run when the Google Visualization API is loaded.
    google.charts.setOnLoadCallback(drawChart);
    function drawChart() {
        var data = google.visualization.arrayToDataTable([
            ['OS', 'Visits'],
            ['windows10', ${windows10}],
            ['windows7', ${windows7}],
            ['ubuntu1604', ${ubuntu1604}],
            ['ubuntu1804', ${ubuntu1804}],
            ['android8', ${android8}],
            ['android9', ${android9}]
        ]);
        var options = {
            title: "Visits by OS",
            width: 650,
            height: 450,
            bar: {groupWidth: "75%"},
            legend: { position: "none" },
        };
        var chart = new google.visualization.PieChart(document.getElementById("donut_values_os"));
        chart.draw(data, options);
    }
</script>

</body>
