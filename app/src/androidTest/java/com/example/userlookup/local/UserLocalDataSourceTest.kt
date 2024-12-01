package com.example.userlookup.local

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.userlookup.UserDB
import com.example.userlookup.UserQueries
import com.example.userlookup.lookupfeature.model.Address
import com.example.userlookup.lookupfeature.model.Company
import com.example.userlookup.lookupfeature.model.Geo
import com.example.userlookup.lookupfeature.model.User
import com.example.userlookup.lookupfeature.model.UserPost
import com.example.userlookup.lookupfeature.model.toDatalayerObject
import com.example.userlookup.lookupfeature.model.toDomainLayerObject
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.junit.runner.RunWith
import kotlin.test.*


@RunWith(AndroidJUnit4::class)
@SmallTest
class UserLocalDataSourceTest {
    private lateinit var database: UserDB
    private lateinit var userQueries: UserQueries


    @BeforeTest
    fun setup() {
        val driver = createTestDriver()
        database = UserDB(driver)
        userQueries = database.userQueries
    }


    @AfterTest
    fun tearDown() {
        userQueries.deleteAllUsers()
    }

    @Test
    fun insertUserItemTest() {
        val userList = getUserList()
        userQueries.transaction {
            userQueries.deleteAllUsers()
            userList.forEach { user ->
                userQueries.insertorReplaceUser(user.toDatalayerObject())
            }
        }
        val result = userQueries.GetAllUser().executeAsList().map { it.toDomainLayerObject() }
        assertEquals(userList, result)

    }

    @Test
    fun checktheUserexistInDB() {
        val user = getUserObject()
        userQueries.transaction {
            userQueries.deleteAllUsers()
            userQueries.insertorReplaceUser(user.toDatalayerObject())
        }
        val result = userQueries.GetUserDetailByName(userName = user.userName).executeAsOne().toDomainLayerObject()

        assertEquals(user,result)
    }
    @Test
    fun insertAndGetUserByName() {
        val user = getUserObject()
        userQueries.transaction {
            userQueries.deleteAllUsers()
            userQueries.insertorReplaceUser(user.toDatalayerObject())
        }
        val result = userQueries.checkUserExist(userName = user.userName).executeAsOne()
        assertTrue(result)
    }

    @Test
    fun insertAndRemoveAllUser(){
        val userList = getUserList()
        userQueries.transaction {
            userList.forEach { user ->
                userQueries.insertorReplaceUser(user.toDatalayerObject())
            }
        }
        userQueries.deleteAllUsers()
        val result = userQueries.GetAllUser().executeAsList().map { it.toDomainLayerObject() }
        assertTrue(result.isEmpty())
    }
    @Test
    fun insertUserPostTest() {
        val userPostList = getUserPostList()
        userQueries.transaction {
            userQueries.deleteAllUsers()
            userPostList.forEach { post ->
                userQueries.insertorReplacePost(post.toDatalayerObject())
            }
        }
        val result = userQueries.GetPostByUser(1).executeAsList().map { it.toDomainLayerObject() }
        assertEquals(userPostList, result)
    }

    @Test
    fun insertAndRemoveUserPost(){
        val userPostList = getUserPostList()
        userQueries.transaction {
            userQueries.deleteAllUsers()
            userPostList.forEach { post ->
                userQueries.insertorReplacePost(post.toDatalayerObject())
            }
        }
        userQueries.deleteAllPostByUser(1)
        val result = userQueries.GetPostByUser(1).executeAsList().map { it.toDomainLayerObject() }
        assertTrue(result.isEmpty())
    }
}
fun createTestDriver(): SqlDriver {
    val app = ApplicationProvider.getApplicationContext<Application>()
    return AndroidSqliteDriver(UserDB.Schema, app, null)
}
fun getUserObject():User
{
    return User(
        userid = 1,
        name = "Leanne Graham",
        userName = "Bret",
        email = "Sincere@april.biz",
        address = Address(
            street = "Kulas Light",
            suite = "Apt. 556",
            city = "Gwenborough",
            zipcode = "92998-3874",
            geo = Geo(lat = "-37.3159", lng = "81.1496")
        ),
        phone = "1-770-736-8031 x56442",
        company = Company(
            companyName = "Romaguera-Crona",
            catchPhrase = "Multi-layered client-server neural-net",
            bs = "harness real-time e-markets"
        )
    )
}
fun getUserList():List<User>
{
    return listOf(getUserObject())
}
fun getUserPostObject():UserPost
{
    return UserPost(
        userId = 1,
        postId = 1,
        title = "title content",
        body = "body content fo android"
    )
}
fun getUserPostList():List<UserPost>
{
    return listOf(getUserPostObject())
}