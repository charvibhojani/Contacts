package com.example.contacts;

import android.app.Activity;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;

public class ShowActivity extends AppCompatActivity implements ShowAdapter.Myclick {

    RecyclerView lv;
    String select = "";

    ArrayList<ShowItem> showItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_show);

        lv = findViewById(R.id.lv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lv.setLayoutManager(linearLayoutManager);

        Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();

        dataload();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home_menu, menu);

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();

        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Log.d("TAG", "onQueryTextSubmit: " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("TAG", "onQueryTextChange: " + newText);
                ShowAdapter.filter(newText);
                ShowAdapter.notifyDataSetChanged();

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.share) {
            Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
        }

        if (item.getItemId() == R.id.ep) {
            Intent intent = new Intent(ShowActivity.this, Editcontact.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.cp) {
            Intent intent = new Intent(ShowActivity.this, Changepassword.class);
            startActivity(intent);
            finish();
        }
        if (item.getItemId() == R.id.logout) {
            Intent intent = new Intent(ShowActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
//            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.fav) {
            Intent intent = new Intent(ShowActivity.this, Favourite.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void more(int pos, View view) {

        PopupMenu popupMenu = new PopupMenu(ShowActivity.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.contact_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.et) {
                    Toast.makeText(ShowActivity.this, "" + showItems.get(pos).getId(), Toast.LENGTH_SHORT).show();

                    MyApp.setCID(showItems.get(pos).getId());

                    Intent intent = new Intent(ShowActivity.this, UpdateActivity.class);
                    startActivity(intent);
                    finish();

                }

                if (menuItem.getItemId() == R.id.delete) {

                    MyApp.db.execSQL("delete from con where id='" + showItems.get(pos).getId() + "'");
                    dataload();
                    Toast.makeText(ShowActivity.this, "Contact deleted successfully", Toast.LENGTH_SHORT).show();
                }
                if (menuItem.getItemId() == R.id.copy) {
                    String data = showItems.get(pos).getNumber();

                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", data);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(ShowActivity.this, "Copied Successfully", Toast.LENGTH_SHORT).show();
                }

                if (menuItem.getItemId() == R.id.share) {
                    String msg = showItems.get(pos).getNumber() + " and " + showItems.get(pos).getName();
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
                    sendIntent.setType("text/plain");

                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    startActivity(shareIntent);

                }

                if (menuItem.getItemId() == R.id.call) {

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + showItems.get(pos).getNumber()));
                    startActivity(intent);
                    finish();

                }
                if (menuItem.getItemId() == R.id.fav) {

                    Cursor cursor = MyApp.db.rawQuery("select * from fav where cid='" + showItems.get(pos).getId() + "'", null);

                    if (cursor != null) {

                        if (cursor.moveToNext()) {

                            MyApp.db.execSQL("delete from fav where cid='" + showItems.get(pos).getId() + "'");
                            Toast.makeText(ShowActivity.this, "Removed from favorites", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ShowActivity.this, "Added to favorites", Toast.LENGTH_SHORT).show();
                            MyApp.db.execSQL("insert into fav(cid,uid) values('" + showItems.get(pos).getId() + "','" + MyApp.getUID() + "')");
                        }
                    }
                }
                return false;
            }
        });
        popupMenu.show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        dataload();
    }

    public void dataload() {

        showItems.clear();
        Cursor cursor = MyApp.db.rawQuery("select * from con where uid='" + MyApp.getUID() + "'", null);
        if (cursor != null) {

            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String path = cursor.getString(1);
                String name = cursor.getString(2);
                String number = cursor.getString(3);
                String address = cursor.getString(4);
                String uid = cursor.getString(5);
                Log.d("TAG", "dataload: " + id);
                showItems.add(new ShowItem(id, path, name, number, address, uid));
                Log.d("TAG", "onCreate: " + id);
            }
            Collections.reverse(showItems);

            ShowAdapter = new ShowAdapter(showItems, ShowActivity.this, ShowActivity.this);
            lv.setAdapter(ShowAdapter);
        }
    }

    ShowAdapter ShowAdapter;

    public void getmypos(int pos, View view) {

        String name = showItems.get(pos).getName();

        Toast.makeText(this, "" + name, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(ShowActivity.this, HomeActivity.class);
        intent.putExtra("title", select);
        startActivity(intent);
        finish();

    }

    public void showcpdialog() {
        Dialog dialog = new Dialog(ShowActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog.setContentView(R.layout.dialog_forgot_password);
        dialog.setTitle("Change password");
        dialog.setCancelable(true);

        dialog.show();
    }
}