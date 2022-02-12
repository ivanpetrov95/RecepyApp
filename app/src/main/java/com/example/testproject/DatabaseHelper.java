package com.example.testproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.testproject.Entities.ProductEntity;
import com.example.testproject.Entities.RecepyEntity;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DB_NAME = "realdb.db";
    private static final int DB_VERSION = 1;
    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String recepiesTableSql = "CREATE TABLE IF NOT EXISTS \"recepies\" (\n" +
                "\t\"ID\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"recepy_name\"\tTEXT NOT NULL UNIQUE,\n" +
                "\t\"recepy_description\"\tTEXT NOT NULL\n" +
                ");";
        String middleTableSql = "CREATE TABLE IF NOT EXISTS \"connectionTable\"(\n"+
                "\t\"recepyID\"\tINTEGER NOT NULL,\n"+
                "\t\"productID\"\tINTEGER NOT NULL,\n"+
                "\tUNIQUE\t(\"recepyID\", \"productID\")\tON CONFLICT ABORT, \n"+
                "\tFOREIGN KEY\t(\"productID\")\tREFERENCES\t\"products(ID)\",\n"+
                "\tFOREIGN KEY\t(\"recepyID\")\tREFERENCES\t\"recepies(ID)\"\n"+
                ");";
        String productsTableSql = "CREATE TABLE IF NOT EXISTS \"products\"(\n"+
                "\t\"ID\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"product_name\"\tTEXT NOT NULL UNIQUE\n" +
                ");";
        db.execSQL(recepiesTableSql);
        db.execSQL(productsTableSql);
        db.execSQL(middleTableSql);
    }

    public long addRecepy(String recepyName, String recepyDesc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("recepy_name", recepyName);
        contentValues.put("recepy_description", recepyDesc);

        long resultRowId = db.insert("recepies", null, contentValues);
        db.close();
        return resultRowId;
    }

    public ArrayList<RecepyEntity> getAllRecepies(){
        ArrayList<RecepyEntity> arrayList = new ArrayList<RecepyEntity>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor results = db.rawQuery("SELECT * FROM recepies", null);
        results.moveToFirst();
        while (results.isAfterLast() == false){
            arrayList.add(new RecepyEntity(
                    results.getInt(results.getColumnIndex("ID")),
                    results.getString(results.getColumnIndex("recepy_name")),
                    results.getString(results.getColumnIndex("recepy_description"))
            ));
            results.moveToNext();
        }
        results.close();
        return arrayList;
    }

    public ArrayList<ProductEntity> getAllProducts()
    {
        ArrayList<ProductEntity> productsEntities = new ArrayList<ProductEntity>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor results = db.rawQuery("SELECT * FROM products", null);
        results.moveToFirst();
        while(results.isAfterLast() == false)
        {
            ProductEntity newProductEntity = new ProductEntity(
                    results.getString(results.getColumnIndex("product_name"))
            );
            newProductEntity.setID(results.getInt(results.getColumnIndex("ID")));
            productsEntities.add(newProductEntity);
            results.moveToNext();
        }
        results.close();
        return productsEntities;
    }

    public boolean addProduct(int recepyID, String productName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("product_name", productName);
        Cursor cursor = db.rawQuery("SELECT * FROM products WHERE product_name = ?", new String[]{productName});
        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            ContentValues ecv = new ContentValues();
            ecv.put("recepyID", recepyID);
            ecv.put("productID", cursor.getInt(cursor.getColumnIndex("ID")));
            long resultInsertWhenAvailable = db.insert("connectionTable", null, ecv);
        }
        else
        {
            long result = db.insert("products", null, cv);
            ContentValues acv = new ContentValues();
            acv.put("recepyID", recepyID);
            acv.put("productID", result);
            long resultConnection = db.insert("connectionTable", null, acv);
        }
        db.close();
        return true;
    }

    public void removeRecepy(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("recepies","ID = ?", new String[]{Integer.toString(id)});
        db.delete("connectionTable", "recepyID = ?", new String[]{Integer.toString(id)});
        db.close();
    }

    public void removeProductFromRecepy(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("connectionTable", "productID = ?", new String[]{Integer.toString(id)});
        db.close();
    }

    public void removeProductCompletely(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM connectionTable WHERE productID = ?", new String[]{Integer.toString(id)});
        cursor.moveToFirst();
        if(cursor.getCount() == 0)
        {
            db.delete("products","ID = ?", new String[]{Integer.toString(id)});
        }
        db.close();
    }

    public RecepyEntity findRecepyById(long id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM recepies WHERE ID = ?", new String[]{Long.toString(id)});
        RecepyEntity resultedRecepy = null;
        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            resultedRecepy = new RecepyEntity(
                                                cursor.getInt(cursor.getColumnIndex("ID")),
                                                cursor.getString(cursor.getColumnIndex("recepy_name")),
                                                cursor.getString(cursor.getColumnIndex("recepy_description"))
            );
        }
        cursor.close();
        return resultedRecepy;
    }

    public void editRecepy(RecepyEntity recepyToBeEdited)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("recepy_name", recepyToBeEdited.getRecepyName());
        cv.put("recepy_description", recepyToBeEdited.getRecepyDescription());
        db.update("recepies",cv, "ID = ?", new String[]{Integer.toString(recepyToBeEdited.getId())});
    }

    public void editProduct(ProductEntity productToBeEdited)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("product_name", productToBeEdited.getProductName());
        db.update("products",cv, "ID = ?", new String[]{Integer.toString(productToBeEdited.getID())});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        String recepiesSql = "DROP TABLE IF EXISTS recepies";
        String productsSql = "DROP TABLE IF EXISTS products";
        String connectionTableSql = "DROP TABLE IF EXISTS connectionTable";
        db.execSQL(recepiesSql);
        db.execSQL(productsSql);
        db.execSQL(connectionTableSql);
        onCreate(db);
    }

    public ArrayList<ProductEntity> getAllProductsOfRecepy(int id) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<ProductEntity>productsList = new ArrayList<ProductEntity>();
        Cursor results = db.rawQuery("SELECT productID, ID, product_name FROM connectionTable INNER JOIN products ON products.ID = connectionTable.productID WHERE recepyID = ?", new String[]{Integer.toString(id)});
        results.moveToFirst();
        while (results.isAfterLast() == false)
        {
            ProductEntity productEntity = new ProductEntity(results.getString(results.getColumnIndex("product_name")));
            productEntity.setID(results.getInt(results.getColumnIndex("ID")));
            productsList.add(productEntity);
            results.moveToNext();
        }
        return productsList;
    }
}
