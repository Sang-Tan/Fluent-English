class DataTableManager {
    constructor(changePositionUrl) {
        this.originalPositionMap = this.getCurrentPositionMap();
        this.changedPositionMap = {};
        this.changePositionUrl = changePositionUrl;
    }

    getCurrentPositionMap() {
        const currentData = $('#' + DATA_TABLE_ID).DataTable().rows().data();
        const orderMap = {};
        for (let i = 0; i < currentData.length; i++) {
            orderMap[currentData[i][1]] = currentData[i][0];
        }
        return orderMap;
    }

    trackChange(id, newPosition) {
        this.changedPositionMap[id] = newPosition;
    }

// Function to send only the changed data to the server
    sendChangesToServer() {
// Wrap changedDataMap in an object with the key "idPositionMap"
        const jsonData = JSON.stringify({"idPositionMap": this.changedPositionMap});

// Reference to the success and fail message divs
        const successDiv = document.getElementById("success-reposition");
        const failDiv = document.getElementById("fail-reposition");

// Send the JSON data to the server using AJAX
        $.ajax({
            type: "POST",
            url: this.changePositionUrl,
            contentType: "application/json",
            data: jsonData,
            success: function (response) {
// Show the success message div and hide the fail message div
                successDiv.style.display = "block";
                failDiv.style.display = "none";
            },
            error: function (error) {
// Show the fail message div and hide the success message div
                failDiv.style.display = "block";
                successDiv.style.display = "none";
            }
        });
    }
// Function to save a position change and track it
    savePosition() {
        const currentPositionMap = this.getCurrentPositionMap();

// Check if the position has changed
        for (const id in currentPositionMap) {
            if (currentPositionMap[id] !== this.originalPositionMap[id]) {
                this.trackChange(id, currentPositionMap[id]);
            }
        }

// check if there are any changes to send
        if (Object.keys(this.changedPositionMap).length > 0) {
            this.sendChangesToServer();
        }
    }
}

const DATA_TABLE_ID = 'data_table';
const IS_PAGINABLE = [[${dataTable.isPaginable}]];
const IS_ARRANGEABLE = [[${dataTable.isArrangeable}]];
const CHANGE_POSITION_URL = "[[${dataTable.changePositionsUrl}]]";
function initializeDataTable() {
    const dataTableConfig = {
        paging: IS_PAGINABLE,
        rowReorder: {
            selector: 'tr',
            enabled: IS_ARRANGEABLE
        },
        fixedColumns: true,
        columnDefs: [{ width: 700, targets: -3 }]
    };

    new DataTable('#' + DATA_TABLE_ID, dataTableConfig);
}

const dataTableManager = new DataTableManager(CHANGE_POSITION_URL);
// Call the initializeDataTable function to initialize the DataTable
initializeDataTable();