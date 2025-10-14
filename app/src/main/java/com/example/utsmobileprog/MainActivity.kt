package com.example.utsmobileprog

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.utsmobileprog.R   // <- pastikan R yang dipakai dari package aplikasi, BUKAN android.R

class MainActivity : ComponentActivity() {

    // ====== DATA TOKO (boleh kamu ubah) ======
    private val storeName = "TOKO SERBA ADA"
    private val tagline   = "Ada Kita • Toko Serba Ada"

    private val phone     = "+62 812-3456-7890"
    private val igHandle  = "@adakita.store"
    private val email     = "halo@adakita.store"
    private val address   = "Jl. Mataram No. 88, NTB"

    // link
    private val igUrl     = "https://instagram.com/${igHandle.removePrefix("@")}"
    private val mapsQuery = "geo:0,0?q=${Uri.encode(address)}"
    private val catalogUrl = "https://shopee.co.id/adakita.store"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Skema warna selaras logo
            val primaryGreen   = Color(0xFF3E6F59)
            val secondaryGreen = Color(0xFF8BAA95)
            val bg             = Color(0xFFF3F7F4)

            MaterialTheme(
                colorScheme = androidx.compose.material3.lightColorScheme(
                    primary = primaryGreen,
                    secondary = secondaryGreen,
                    background = bg,
                    surface = Color.White,
                    onPrimary = Color.White
                )
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BusinessCard(
                        storeName = storeName,
                        tagline = tagline,
                        phone = phone,
                        igHandle = igHandle,
                        email = email,
                        address = address,
                        igUrl = igUrl,
                        mapsQuery = mapsQuery,
                        catalogUrl = catalogUrl
                    )
                }
            }
        }
    }
}

@Composable
fun BusinessCard(
    storeName: String,
    tagline: String,
    phone: String,
    igHandle: String,
    email: String,
    address: String,
    igUrl: String,
    mapsQuery: String,
    catalogUrl: String
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F7F4))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Kartu bagian atas: Logo + Nama + Tagline
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_toko_serba_ada),
                    contentDescription = "Logo Toko Serba Ada",
                    modifier = Modifier.size(120.dp)
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = storeName,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = tagline,
                    fontSize = 14.sp,
                    color = Color(0xFF3E6F59),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        // Kartu bawah: Kontak interaktif
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(Modifier.padding(16.dp)) {
                ContactRow(
                    icon = Icons.Filled.Call,
                    text = phone
                ) {
                    // Dialer
                    context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone")))
                }

                Divider(Modifier.padding(vertical = 8.dp), color = Color(0xFFE6E6E6))

                ContactRow(
                    icon = Icons.Filled.Share,
                    text = igHandle
                ) {
                    // Instagram
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(igUrl)))
                }

                Divider(Modifier.padding(vertical = 8.dp), color = Color(0xFFE6E6E6))

                ContactRow(
                    icon = Icons.Filled.Email,
                    text = email
                ) {
                    // Email
                    val mail = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
                    mail.putExtra(Intent.EXTRA_SUBJECT, "Halo $storeName")
                    context.startActivity(mail)
                }

                Divider(Modifier.padding(vertical = 8.dp), color = Color(0xFFE6E6E6))

                ContactRow(
                    icon = Icons.Filled.LocationOn,
                    text = address
                ) {
                    // Maps
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mapsQuery)))
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        // Tombol Lihat Katalog
        Button(
            onClick = {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(catalogUrl)))
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3E6F59))
        ) {
            Icon(Icons.Filled.ShoppingCart, contentDescription = null, tint = Color.White)
            Spacer(Modifier.width(8.dp))
            Text("Lihat Katalog", color = Color.White, fontSize = 16.sp)
        }

        Spacer(Modifier.weight(1f))
    }
}

@Composable
private fun ContactRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 6.dp)
    ) {
        Icon(icon, contentDescription = null, tint = Color(0xFF3E6F59))
        Spacer(Modifier.width(12.dp))
        Text(text, textAlign = TextAlign.Start, modifier = Modifier.weight(1f))
    }
}
