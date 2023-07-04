package com.dgby.jxc.activity.role;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Collections;
import java.util.List;
import com.dgby.jxc.R;

public class RoleActivity extends AppCompatActivity implements RoleAdapter.ItemClickListener {

    private RoleDbHelper dbHelper;
    private RecyclerView recyclerView;
    private RoleAdapter adapter;
    private List<Role> roleList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);

        // 初始化数据库帮助类
        dbHelper = new RoleDbHelper(this);

        // 初始化 RecyclerView 和适配器

        recyclerView = findViewById(R.id.role_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration());
        adapter = new RoleAdapter(this);
        recyclerView.setAdapter(adapter);


        // 读取数据库中的角色列表并显示在 RecyclerView 上
        roleList = dbHelper.getAllRoles();
        adapter.setRoleList(roleList);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 加载菜单项
        getMenuInflater().inflate(R.menu.menu_role, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add) {
            // 显示添加角色的对话框
            showAddRoleDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view, int position) {
        // 显示编辑角色的对话框
        showEditRoleDialog(position);
    }

    @Override
    public void onItemLongClick(View view, int position) {
        // 显示删除角色的对话框
        showDeleteRoleDialog(position);
    }

    private void showAddRoleDialog() {
        // 构造添加角色的对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_add_role_title);

        // 添加输入框
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // 添加确定按钮
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = input.getText().toString().trim();

                // 添加新角色到数据库中
                long id = dbHelper.addRole(name);

                // 创建新的 Role 对象并添加到角色列表中
                Role role = new Role(Integer.valueOf((int) id), name);
                roleList.add(role);

                // 刷新 RecyclerView
                adapter.notifyItemInserted(roleList.size() - 1);
            }
        });

        // 添加取消按钮
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // 显示对话框
        builder.show();
    }

    private void showEditRoleDialog(final int position) {
        // 构造编辑角色的对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_edit_role_title);

        // 添加输入框
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(roleList.get(position).getName());
        builder.setView(input);

        // 添加确定按钮
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = input.getText().toString().trim();
                Role  rolel = new Role(roleList.get(position).getId(), name);
                // 更新数据库中对应角色的名称
                dbHelper.updateRole(rolel);

                // 更新对应的 Role 对象
                Role role = roleList.get(position);
                role.setName(name);

                // 刷新 RecyclerView
                adapter.notifyItemChanged(position);
            }
        });

        // 添加取消按钮
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // 显示对话框
        builder.show();
    }
    @SuppressLint("StringFormatInvalid")
    private void showDeleteRoleDialog(final int position) {
        // 构造删除角色的对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_delete_role_title);

        // 设置提示消息
        String message = getString(R.string.dialog_delete_role_message, roleList.get(position).getName());
        builder.setMessage(message);

        // 添加确定按钮
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 从数据库中删除对应的角色
                dbHelper.deleteRole(roleList.get(position).getId());

                // 从角色列表中删除对应的 Role 对象
                roleList.remove(position);

                // 刷新 RecyclerView
                adapter.notifyItemRemoved(position);
            }
        });

        // 添加取消按钮
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // 显示对话框
        builder.show();
    }
}

