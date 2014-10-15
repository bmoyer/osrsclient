/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsclient.market;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ben
 */
public class OfferModel extends AbstractTableModel {

    private List<String[]> rows;

    public OfferModel() {
        rows = new ArrayList<>(25);
    }

    private String[] columnNames = {"?", "QTY", "Price", "RSN"};

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String[] row = rows.get(rowIndex);
        return row[columnIndex];
    }

    public void addRow(String buyOrSell, String qty, String price, String rsn) {
        int rowCount = getRowCount();
        String[] row = new String[getColumnCount()];

        row[0] = buyOrSell;
        row[1] = NumberFormat.getIntegerInstance().format(Integer.parseInt(qty));
        row[2] = NumberFormat.getIntegerInstance().format(Integer.parseInt(price));
        row[3] = rsn;

        rows.add(row);
        fireTableRowsInserted(rowCount, rowCount);
    }

    public void clearRows() {
        int numRows = getRowCount();
        if (numRows == 0) {
            return;
        }
        rows.clear();
        fireTableRowsDeleted(0, numRows - 1);
    }

}
