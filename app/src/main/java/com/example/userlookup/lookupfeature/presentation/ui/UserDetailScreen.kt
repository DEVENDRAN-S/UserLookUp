package com.example.userlookup.lookupfeature.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.userlookup.R
import com.example.userlookup.lookupfeature.presentation.UserLookUpViewModel
import com.example.userlookup.ui.theme.primaryBaseColor
import com.example.userlookup.ui.theme.primaryTextColor


@Composable
fun UserDetailScreen(lookUpViewModel: UserLookUpViewModel) {
    lookUpViewModel.userData?.let{user ->
        Surface(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            color = primaryBaseColor
        ) {

            Surface(
                color = Color.White,
                modifier = Modifier.padding(16.dp),
                shadowElevation = 8.dp,
                shape = RoundedCornerShape(4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                ) {

                    Text(text= stringResource(id = R.string.personal_information),color = Color.Black, fontWeight = FontWeight.Bold)
                    Divider(color = primaryTextColor, thickness = 2.dp, modifier = Modifier.padding(vertical = 5.dp))
                    TitleWithContent(R.string.name,user.name)
                    TitleWithContent(R.string.email,user.email)
                    TitleWithContent(R.string.phone,user.phone)
                    TitleWithContent(R.string.name,user.name)
                    TitleWithContent(R.string.address,"${user.address.street},${user.address.suite}\n${user.address.city}" )
                    TitleWithContent(R.string.zipcode,user.address.zipcode)

                    Text(text= stringResource(id = R.string.company_information),color = Color.Black, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top=5.dp))
                    Divider(color = primaryTextColor, thickness = 2.dp, modifier = Modifier.padding(vertical = 5.dp))
                    TitleWithContent(R.string.company_name,user.company.companyName)
                    TitleWithContent(R.string.catchPhrase,user.company.catchPhrase)
                    TitleWithContent(R.string.bs,user.company.bs)

                }
            }
        }
    }
}


@Composable
fun TitleWithContent(titleResource:Int, content :String) {
    val title = stringResource(id = titleResource)
    Row(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
    ) {

        Text(
            text = title,
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp
        )
        Text(
            text = ": $content",
            modifier = Modifier.weight(2f),
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        )
    }

}
