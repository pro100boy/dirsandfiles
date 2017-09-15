var subdirDatatableAPI;
var subAjaxUrl;

$(function () {

    subdirDatatableAPI = $('#subdirsTable').DataTable(extendsOpts(subAjaxUrl, {
        "columns": [
            {
                "data": "path"
            },
            {
                "data": "size",
                "render": function (date, type, row) {
                    if (type == 'display') {
                        return date.replace('DIR', '&lt;DIR&gt;');
                    }
                    return date;
                }
            }
        ],
        "columnDefs": [
            {"orderable": false, "targets": [0, 1]},
            {"width": "65%", "targets": 0}
        ],
        "aaSorting" : []
    }));
});