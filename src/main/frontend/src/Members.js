import { useState, useEffect } from 'react';
import { AgGridReact } from 'ag-grid-react';
import 'ag-grid-community/styles/ag-grid.css';
import 'ag-grid-community/styles/ag-theme-alpine.css';

const Members = ({ members }) => {

    const columnDefs = [
        { field: 'id', headerName: 'ID' },
        { field: 'name', headerName: 'Name' },
        { field: 'email', headerName: 'Email' },
        { field: 'phoneNumber', headerName: 'Phone' },
        {
            field: 'restUrl', headerName: 'Rest URL',
            cellRenderer: props => {
                const url = "http://localhost:8081/kitchensinknew/rest/member/" + props.data.id
                const val = "rest/member/" + props.data.id
                return <>
                    <a href={url}>{val}</a>
                </>;
            }
        },
    ];
    return (
        <>
            <h4>Members</h4>
            <div className="ag-theme-alpine" style={{ height: 400, width: '100%' }}>
                <AgGridReact
                    rowData={members}
                    enableCellTextSelection={true}
                    columnDefs={columnDefs}
                />
            </div>
        </>
    )
}

export default Members;