package com.mobileartslab.ma_nav_android

import DataModel
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mobileartslab.ma_nav_android.ui.theme.Purple40
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun LoginScreen(navController: NavHostController) {
  val username = remember { mutableStateOf(TextFieldValue()) }
  val password = remember { mutableStateOf(TextFieldValue()) }
  var isValid: MutableState<Boolean> = remember { mutableStateOf(true) }
  val usernameError = remember { mutableStateOf("") }
  val passwordError = remember { mutableStateOf("") }
  val submitError = remember { mutableStateOf("") }


  fun validate() : Boolean {
    isValid.value = true
    usernameError.value = ""
    passwordError.value = ""
    submitError.value = ""

    if (username.value.text.isEmpty()) {
      usernameError.value = "Username required"
      isValid.value = false
    }
    else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(username.value.text).matches()) {
      usernameError.value = "Invalid email"
      isValid.value = false
    }
    if (password.value.text.isEmpty()) {
      passwordError.value = "Password required"
      isValid.value = false
    }
    return isValid.value
  }

  fun login() {
    var url = "http://192.168.86.29:8000/api/public/"
    val retrofit =
      Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofitAPI = retrofit.create(RetrofitAPI::class.java)
    val dataModel = DataModel(username.value.text, password.value.text, "", "")
    val call: Call<DataModel?>? = retrofitAPI.postData(dataModel)

    call!!.enqueue(object : Callback<DataModel?> {
      override fun onResponse(call: Call<DataModel?>?, response: Response<DataModel?>) {
        Log.d("LOG RESPONSE:", response.code().toString())
        Log.d("LOG RESPONSE BODY:", response.body().toString())
        navController.navigate(Routes.Dashboard.route)
      }

      override fun onFailure(call: Call<DataModel?>?, t: Throwable) {
        Log.d("LOG ERROR FOUND:", t.message!!)
      }
    })
  }

  fun onSubmit() {
    if (!validate()) {
      return
    }
    login()
  }

  Box(modifier = Modifier.fillMaxSize()) {
    ClickableText(
      text = AnnotatedString("Sign up here"),
      modifier = Modifier.align(Alignment.BottomCenter).padding(20.dp),
      onClick = { navController.navigate(Routes.SignUp.route) },
      style = TextStyle(
        fontSize = 14.sp,
        fontFamily = FontFamily.Default,
        textDecoration = TextDecoration.Underline,
        color = Purple40
      )
    )
  }
  Column(
    modifier = Modifier.padding(20.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(text = "Communicator", style = TextStyle(fontSize = 40.sp))
    Spacer(modifier = Modifier.size(40.dp))
    Image(
      painter = painterResource(id = R.drawable.nav_login),
      contentDescription = "",
      modifier = Modifier
        .size(200.dp)
        .padding(5.dp)
    )

    Spacer(modifier = Modifier.height(40.dp))

    TextField(
      label = { Text(text = "Username") },
      value = username.value,
      onValueChange = { username.value = it }
    )
    Text(
      modifier = Modifier.fillMaxWidth(1f).padding(start = 35.dp),
      text = usernameError.value,
      fontSize = 14.sp,
      color = Color.Red
    )

    Spacer(modifier = Modifier.height(20.dp))
    TextField(
      label = { Text(text = "Password") },
      value = password.value,
      visualTransformation = PasswordVisualTransformation(),
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
      onValueChange = { password.value = it }
    )
    Text(
      modifier = Modifier.fillMaxWidth(1f).padding(start = 35.dp),
      text = passwordError.value,
      fontSize = 14.sp,
      color = Color.Red
    )

    Spacer(modifier = Modifier.height(20.dp))
    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
      Button(
        onClick = { onSubmit() },
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier.fillMaxWidth().height(50.dp)
      ) {
        Text(text = "Login")
      }
    }
    Text(
      modifier = Modifier.padding(start = 2.dp),
      text = submitError.value,
      fontSize = 14.sp,
      color = Color.Red
    )

    Spacer(modifier = Modifier.height(20.dp))
    ClickableText(
      text = AnnotatedString("Forgot password?"),
      onClick = { navController.navigate(Routes.ForgotPassword.route) },
      style = TextStyle(
        fontSize = 14.sp,
        fontFamily = FontFamily.Default
      )
    )
  }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
  LoginScreen(navController = rememberNavController())
}