
function initializeDataTable(id, config) {
    let dataTableConfig;
    if(config.isArrangeable) {
        dataTableConfig = {
            paging: config.isPaginable,
            rowReorder: {
                enabled: true,
                selector: 'tr'
            },
            fixedColumns: true,
            columnDefs: [{ width: 700, targets: -3 }]
        }
        // build a success message notification
        const successDiv = $('<div>', {
            id: 'success-reposition',
            class: 'alert alert-success',
            text: 'Successfully saved',
            style: 'display: none'
        });
        // build a fail message notification
        const failDiv = $('<div>', {
            id: 'fail-reposition',
            class: 'alert alert-danger',
            text: 'Failed to save',
            style: 'display: none'
        });
        const btn = createSavePositionButton(id, config.changePositionsUrl, successDiv, failDiv);
        btn.after(successDiv);
        btn.after(failDiv);
    }
    else {
        dataTableConfig = {
            paging: config.isPaginable,
            fixedColumns: true,
            columnDefs: [{ width: 700, targets: -3 }]
        }
    }
    new DataTable('#' + id, dataTableConfig);
}

function createSavePositionButton(tableId, changePositionsUrl, successDiv, failDiv) {
    const btn = $('<button>', {
        class: 'btn btn-primary',
        text: 'Save Position',
        click: function () {
            originalDatatablesMap[tableId].savePosition(successDiv, failDiv);
        }
    })

    // Add the button after the table
    $('#' + tableId).after(btn);
    return btn;
}



class DataTableManager {
    constructor(id, changePositionUrl) {
        this.id = id;
        this.changePositionUrl = changePositionUrl;
        this.originalPositionMap = this.getCurrentPositionMap();
    }

    getCurrentPositionMap() {
        const currentData = $('#' + this.id).DataTable().rows().data();
        const orderMap = {};
        for (let i = 0; i < currentData.length; i++) {
            orderMap[currentData[i][1]] = currentData[i][0];
        }
        return orderMap;
    }


    // Function to send only the changed data to the server
    sendChangesToServer(successDiv, failDiv, changedPositionMap) {
        // Wrap changedDataMap in an object with the key "idPositionMap"
        const jsonData = JSON.stringify({"idPositionMap": changedPositionMap});


        // Send the JSON data to the server using AJAX
        $.ajax({
            type: "POST",
            url: this.changePositionUrl,
            contentType: "application/json",
            data: jsonData,
            success: function () {
                // Show the success message div and hide the fail message div
                successDiv.css("display", "block");
                failDiv.css("display", "none");
            },
            error: function () {
                // Show the fail message div and hide the success message div
                successDiv.css("display", "none");
                failDiv.css("display", "block");
            }
        });
    }
    // Function to save a position change and track it
    savePosition(successDiv, failDiv) {
        const currentPositionMap = this.getCurrentPositionMap();

        let changedPositionMap = {};
        // Check if the position has changed
        for (const id in currentPositionMap) {
            if (currentPositionMap[id] !== this.originalPositionMap[id]) {
                changedPositionMap[id] = currentPositionMap[id];
            }
        }

        // check if there are any changes to send
        if (Object.keys(changedPositionMap).length > 0) {
            this.sendChangesToServer( successDiv, failDiv, changedPositionMap);
        }
    }
}

let originalDatatablesMap = {};
// Call the initializeDataTable function to initialize the DataTable
for (const each of dataTables) {
    initializeDataTable(each.id, each.config);
    originalDatatablesMap[each.id] = new DataTableManager(each.id, each.config.changePositionsUrl);
}
