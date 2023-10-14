
const DataTableInitializer = (function () {
    /**
     * Adds HTML elements for the given arrangeable DataTable
     * @param id ID of the table
     * @param dataTableManager DataTableManager for the table
     */
    const addHtmlElementsForArrangeableDataTable = (id, dataTableManager) => {
        const successDiv = $('<div>', {
            id: 'success-reposition',
            class: 'alert alert-success',
            text: 'Successfully saved',
            style: 'display: none'
        });
        const failDiv = $('<div>', {
            id: 'fail-reposition',
            class: 'alert alert-danger',
            text: 'Failed to save',
            style: 'display: none'
        });
        const saveBtn = $('<button>', {
            class: 'btn btn-primary',
            text: 'Save Position',
            click: function () {
                dataTableManager.savePosition(successDiv, failDiv);
            }
        })
        $('#' + id).after(saveBtn);
        saveBtn.after(successDiv);
        saveBtn.after(failDiv);
    }
    /**
     * Initializes UI DataTable for giving table with the given ID and configuration options.
     * @param {string} id ID of the table to initialize
     * @param {object} config Configuration options for the DataTable
     * @param {boolean} config.isArrangeable If true, enables row reordering
     * @param {boolean} config.isPaginable If true, adds pagination to the DataTable
     */
    const initializeDataTable = (id, config) => {
        let dataTableConfig;
        if(config.isArrangeable) {
            dataTableConfig = {
                paging: config.isPaginable,
                rowReorder: {
                    enabled: true,
                    selector: 'td:nth-child(6)'
                },
                fixedColumns: true,
                columnDefs: [{ width: 700, targets: "name" }]
            }
        }
        else {
            dataTableConfig = {
                paging: config.isPaginable,
                fixedColumns: true,
                columnDefs: [{ width: 700, targets: "name" }]
            }
        }
        new DataTable('#' + id, dataTableConfig);
    }
    /**
     * Initializes DataTableManager for the given DataTable
     * @param datatable DataTable to initialize DataTableManager for
     * @returns {DataTableManager} DataTableManager for the given DataTable
     */
    const initializeDatatableManager = (datatable) => {
        return new DataTableManager(datatable.id, datatable.config.changePositionsUrl);
    }
    /**
     * Initializes all DataTables in the given dataTables array
     * @param {Array} dataTables Array of DataTables to initialize
     */
    const initializeDataTables = (dataTables) => {
        for (const each of dataTables) {
            initializeDataTable(each.id, each.config);
            if(each.config.isArrangeable) {
                const dataTableManager = initializeDatatableManager(each);
                addHtmlElementsForArrangeableDataTable(each.id, dataTableManager);
            }
        }
    }
    return {
        initializeDataTables: initializeDataTables
    }
})();

 /**
 * Class to handle interactions with DataTables
 */
class DataTableManager {
    /**
    * @constructor
    * @property {string} id ID of the DataTable
    * @property {string} changePositionUrl URL to send the changed position data to
    * @property {Map} originalPositionMap Map of the original positions of the rows in the DataTable
    */
    constructor(id, changePositionUrl) {
        this.id = id;
        this.changePositionUrl = changePositionUrl;
        this.originalPositionMap = this.getCurrentPositionMap();
    }

     /**
      * Gets the current positions of the rows in the DataTable
      * @returns {Map} Map of the current positions of the rows in the DataTable
      * @example
      * // Returns {1: 0, 2: 1, 3: 2, 4: 3, 5: 4}
      */
    getCurrentPositionMap() {
        const currentData = $('#' + this.id).DataTable().rows().data();
        const orderMap = new Map();
        for (let i = 0; i < currentData.length; i++) {
            // currentData[i][1] is the id of the row
            // currentData[i][0] is the position of the row
            orderMap.set(currentData[i][1], currentData[i][0]);
        }
        return orderMap;
    }

     /**
      * Sends a POST request to the server with the changed position data
      * @param {object} successDiv used to display a success message
      * @param {object} failDiv used to display a fail message
      * @param {Map} changedPositionMap Map of the changed positions of the rows in the DataTable
      */
    sendChangesToServer(successDiv, failDiv, changedPositionMap) {
        // Wrap changedDataMap in an object with the key "idPositionMap"
        const jsonData = JSON.stringify({"idPositionMap": Object.fromEntries(changedPositionMap)});

        // Send the JSON data to the server using AJAX
        $.ajax({
            type: "POST",
            url: this.changePositionUrl,
            contentType: "application/json",
            data: jsonData,
            success: function () {
                successDiv.css("display", "block");
                failDiv.css("display", "none");
            },
            error: function () {
                successDiv.css("display", "none");
                failDiv.css("display", "block");
            }
        });
    }
     /**
      * Saves the changed position data to the server
      * @param successDiv used to display a success message
      * @param failDiv used to display a fail message
      */
    savePosition(successDiv, failDiv) {
         let changedPositionMap = new Map();

         // Get the current position map
         const currentPositionMap = this.getCurrentPositionMap();

         // add the changed positions to the changedPositionMap
         currentPositionMap.forEach((value, key) => {
                if (value !== this.originalPositionMap.get(key)) {
                    changedPositionMap.set(key, value);
                }
         });

        // check if there are any changes to send
        if (changedPositionMap.size > 0) {
            this.sendChangesToServer(successDiv, failDiv, changedPositionMap);
        }
    }
}