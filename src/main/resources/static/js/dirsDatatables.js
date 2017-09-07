var ajaxUrl = 'dirs_and_files/';
var dirDatatableAPI;

$(function () {
    dirDatatableAPI = $('#dirsTable').DataTable(extendsOpts(ajaxUrl, {
        "columns": [
            {
                "data": "date",
                "render": function (date, type, row) {
                    if (type == 'display') {
                        return formatDate(date);
                    }
                    return date;
                }
            },
            {
                "data": "path"
            },
            {
                "data": "dircount"
            },
            {
                "data": "filescount"
            },
            {
                "data": "size"
            },
            {
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "order": [[0, "desc"]],
        "columnDefs": [
            {"orderable": false, "targets": [1, 2, 3, 4, 5, 6]}
            , {"width": "15%", "targets": 0}
            , {"width": "35%", "targets": 1}
            // align right
            , {"className": "dt-right", "targets": [2, 3]}
        ]
    }));
});