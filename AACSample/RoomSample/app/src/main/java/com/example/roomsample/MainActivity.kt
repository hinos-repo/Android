package com.example.roomsample

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.roomsample.database.AppDatabase
import com.example.roomsample.database.UserDao
import com.example.roomsample.database.UserEntity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity()
{
    private val mJob = Job()
    private val mScope = CoroutineScope(Dispatchers.Main + mJob)
    private val userDao: UserDao by lazy { AppDatabase.getInstance(this).userDao() }
    private var userLiveData: LiveData<List<UserEntity>>? = null
    private lateinit var userListAdapter : UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUi()
        setListener()
    }

    private fun setUi()
    {
        userLiveData = userDao.getAllObservable()
        userLiveData?.observe(this@MainActivity, Observer { list ->
            if (list.isNullOrEmpty())
                tv_guide.visibility = View.GONE
            else
                tv_guide.visibility = View.VISIBLE

            userListAdapter = UserListAdapter(
                {
                    deleteUser(it)
                    Toast.makeText(applicationContext, "삭제 완료: $it", Toast.LENGTH_SHORT).show()
                },
                {
                    Toast.makeText(applicationContext, "수정 완료 $it", Toast.LENGTH_SHORT).show()
                    updateUser(it)
                })
            userListAdapter.submitList(list)
            rv_name.adapter = userListAdapter
        })
    }

    private fun setListener() {
        btn_insert.setOnClickListener {
            if (et_addr.text.toString().isEmpty() || et_name.text.toString().isEmpty()) {
                Toast.makeText(applicationContext, "아이디, 주소는 필수 값입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            insertUser()
        }
    }


    private fun insertUser() {
        mScope.launch {
            withContext(Dispatchers.IO) {
                userDao.insert(UserEntity(name = et_name.text.toString(), address = et_addr.text.toString()))
            }
            withContext(Dispatchers.Main)
            {
                et_addr.setText("")
                et_name.setText("")
            }
        }
    }

    private fun deleteUser(userEntity: UserEntity) {
        mScope.launch {
            withContext(Dispatchers.IO) {
                userDao.delete(userEntity)
            }
        }
    }

    private fun updateUser(userEntity: UserEntity)
    {
        mScope.launch {
            withContext(Dispatchers.IO) {
                userEntity.name = "수정"
                userDao.update(userEntity)
            }
        }
    }
}
