package com.example.healthcareapp.util

class Constants {
    companion object {
        const val BASE_URL = "http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/"
        const val SERVICE_KEY = "Ho%2BO79EuOBas%2BL0GqQ26bphsXGYl%2BizgO3z1Iv9jgKyvAc%2B7MYhEh3ydiBzNL0YZiqliG35KYbdXPu8%2F9DWKSQ%3D%3D"
        const val OUTPUT_TYPE = "json"

        const val QUERY_SERVICE_KEY = "ServiceKey"
        const val QUERY_ITEM_NAME = "itemName"
        const val QUERY_TYPE = "type"
        const val QUERY_NUM_OF_ROWS = "numOfRows"

        const val REQUEST_CODE = 100

        const val CHANNEL_ID = "medicineChannel"
        const val CHANNEL_NAME = "MyMedicineChannel"
        const val CHANNEL_DESCRIPTION = "Channel for medicine alarm"
        const val RECEIVER_TITLE = "약을 복용해주세요!"
        const val RECEIVER_CONTENT = "\"'나의 약 추가' 에서 오른쪽으로 밀어서 약을 섭취해봐요!\""

    }
}