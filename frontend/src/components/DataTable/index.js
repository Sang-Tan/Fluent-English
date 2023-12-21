import { Table } from "react-bootstrap";
import { DragDropContext, Draggable } from "react-beautiful-dnd";
import { StrictModeDroppable } from "src/helpers/StrictModeDroppable";
import { ArrowsMove } from "react-bootstrap-icons";

import styles from "./DataTable.module.scss";
import classNames from "classnames/bind";

const cx = classNames.bind(styles);

const reorder = (list, startIndex, endIndex) => {
  const result = [...list];
  const [removed] = result.splice(startIndex, 1);

  result.splice(endIndex, 0, removed);

  return result;
};

function DataTable({
  columns = [],
  data = [],
  keyName = "id",
  rowReorderable = false,
  onRowReorder = (e) => {},
}) {
  const columnIds = columns.map((column) => column.id);

  const handleDragEnd = (result) => {
    // dropped outside the list
    if (!result.destination) {
      console.log("dropped outside the list");
      return;
    }

    const items = reorder(data, result.source.index, result.destination.index);
    const e = {
      items,
    };

    console.log("handleDragEnd", items);
    onRowReorder(e);
  };

  return (
    <Table bordered>
      <thead>
        <tr>
          {rowReorderable && <th className={cx("reorder-col")}></th>}
          {columns.map((column) => (
            <th key={column.id} style={{ width: column.width }}>
              {column.label}
            </th>
          ))}
        </tr>
      </thead>
      {rowReorderable && (
        <DragDropContext
          onDragEnd={handleDragEnd}
          onBeforeDragStart={(e) => {
            console.log("onBeforeDragStart", e);
          }}
        >
          <StrictModeDroppable
            droppableId="droppable-table"
            renderClone={(provided, snapshot, rubric) => {
              return (
                <div
                  ref={provided.innerRef}
                  {...provided.draggableProps}
                  {...provided.dragHandleProps}
                  className={cx("dragging-block")}
                  isDragging={snapshot.isDragging}
                >
                  <ArrowsMove />
                </div>
              );
            }}
          >
            {(provided, snapshot) => (
              <tbody ref={provided.innerRef} {...provided.droppableProps}>
                {data.map((item, index) => (
                  <Draggable
                    key={item[keyName].toString()}
                    draggableId={item[keyName].toString()}
                    index={index}
                  >
                    {(provided, snapshot) => (
                      <tr
                        ref={provided.innerRef}
                        {...provided.draggableProps}
                        isDragging={snapshot.isDragging}
                      >
                        <td className={cx("reorder-col")}>
                          <span {...provided.dragHandleProps}>
                            <ArrowsMove />
                          </span>
                        </td>
                        {columnIds.map((columnId, index) => (
                          <td
                            key={columnId}
                            style={{ width: columns[index].width }}
                          >
                            {item[columnId]}
                          </td>
                        ))}
                      </tr>
                    )}
                  </Draggable>
                ))}
                {provided.placeholder}
              </tbody>
            )}
          </StrictModeDroppable>
        </DragDropContext>
      )}
      {!rowReorderable && (
        <tbody>
          {data.map((item) => (
            <tr key={item[keyName]}>
              {columnIds.map((columnId) => (
                <td key={columnId}>{item[columnId]}</td>
              ))}
            </tr>
          ))}
        </tbody>
      )}
    </Table>
  );
}

export default DataTable;
