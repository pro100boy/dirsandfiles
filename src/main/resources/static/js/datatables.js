// https://api.jquery.com/jquery.extend/#jQuery-extend-deep-target-object1-objectN
// common options for datatables
function extendsOpts(ajaxUrl, opts) {
    $.extend(true, opts,
        {
            "ajax": {
                "url": ajaxUrl,
                "dataSrc": ""
            },
            "autoWidth": false,
            "ordering": true,
            "paging": false,
            "bFilter": false,
            "info": false, // убираем "отфильтровано N из N записей
            "language": {
                "url": "json/ru.json"
            }
        }
    );
    return opts;
}

function formatDate(date) {
    var year = date.substr(0, 4);
    var month = date.substr(5, 2);
    var dt = date.substr(8, 2);
    var hh = date.substr(11, 2);
    var mm = date.substr(14, 2);
    return dt + '.' + month + '.' + year + ' ' + hh + ':' + mm;
}

function renderEditBtn(data, type, row) {
    if (type == 'display') {
        return '<button class="btn btn-xs btn-primary" id="rowid' + row.id + '" onclick="showModal(' + row.id + ');">' +
            '<span class="glyphicon glyphicon-folder-open" aria-hidden="true"></span></button>';
    }
}

function renderDeleteBtn(data, type, row) {
    if (type == 'display') {
        return '<button class="btn btn-xs btn-danger" id="rowid' + row.id + '" onclick="deleteRow(' + row.id + ');">' +
            '<span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button>';
    }
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            dirDatatableAPI.ajax.reload();
        }
    })
}

function save() {
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: $('#dirsForm').serialize(),
        success: function () {
            dirDatatableAPI.ajax.reload();
            $("#dirsForm")[0].reset();
        }
    }).fail(function (jqXHR, textStatus, thrownError) {
        alert(jqXHR.responseText);
    });
}


function showModal(id) {
    var currentRow = $("#rowid" + id).closest("tr");
    var cell1 = currentRow.find("td:eq(0)").text(); // get current row 1st TD value
    var cell2 = currentRow.find("td:eq(1)").text(); // get current row 2nd TD

    subAjaxUrl = ajaxUrl + id + '/subdirs';
    $('#modalTitle').html(cell2 + " " + cell1);
    $.get(subAjaxUrl, updateTableByData);
    $('#myModal').modal();
}

function updateTableByData(data) {
    subdirDatatableAPI.clear().rows.add(data).draw();
}