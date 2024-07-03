package com.app.kalyanbazar.utils

import android.content.Context
import android.content.SharedPreferences


class Constants {
    companion object {
        const val DATE_PATTERN = "YYYY-MM"
        const val MONTH_PATTERN = "MMM"
        const val MOBILE = "mobile"
        const val SALARY = "salary"
        const val SALARY_DUE = "salary_due"
        const val totalAdvanceAMOUNT = "totalAdvanceAMOUNT"
        const val SALARY_Payment = "salary_Payent"
        const val PPL_PRICE_ID = "pplpriceId"
        const val PPL_PRICE = "pplprice"
        const val MODULE_ID = "moduleId"
        const val FEATURE_ID = "featureId"
        const val FEATURE_SUB_ID = "featureSubId"
        const val EMPLOYEE_ID = "employeeId"
        const val Date = "date"
        const val YESTERDAY_OUT_TIME = "yesterday_out_time"
        const val YESTERDAY_DATE = "yesterday_date"
        const val EMPLOYEE_PID = "employeePId"
        const val LEAD_ID = "leadId"
        const val ReshaduleType = "reschedule_type"
        const val ReshaduleType_Value = "1"
        const val DATE_TYPE = "dateType"
        const val SIteDetail_ID = "siteDetailId"
        const val BRAND_NAME = "brandName"
        private var instance: SharedPreferences? = null

        const val WAREHOUSE_ID = "warehouseId"
        const val VENDOR_ID = "vendorId"
        const val VENDOR_NAME = "vendorName"
        const val DELEIVERY_DATE = "deleiverydate"
        const val REMAINDER_DATE = "remainderdate"
        const val TOTAL_ITEM = "totalitem"
        const val TOTAL_AMO = "TOTAL_AMO"
        const val PRODUCT_ID = "productId"
        const val PRODUCT_COST = "productCost"
        const val PRODUCT_QTY = "productQty"
        const val SITE_TYPE = "siteType"
        const val PO_ID = "poid"
        const val SITE_NAME = "siteName"
        const val ORDER_DETAIL_ID = "orderDetailID"
        const val RECEIVED_QTY = "ReceivedQty"
        const val RECEIVED_QTY1 = "ReceivedQtyNew"
        const val PENDING_QTY1 = "ReceivedQtyNew"

        const val visit_work_ID = "visitWorkId"

        const val SCHEDULE_TYPE = "scheduleType"
        const val VISIT_ID = "visitId"
        const val VISIT_DETAIL_ID = "visitdetailId"
        const val LEAD_MEETING_ID = "leadmeetingId"
        const val MARKET_ID = "marketId"
        const val SHOW_OPEN = "showOpen"
        const val MACHINE_DETAIL_ID = "MACHINE_DETAIL_ID"
        const val VENDOR_DETAIL_ID = "VENDOR_DETAIL_ID"
        const val FROM_PAGE = "from_page"
        const val Demo_ID = "demoId"
        const val PreviousMeeting_ID = "PreviousMeetingID"
        const val HideButton = "HideButton"
        const val Meeting_ID = "MeetingID"
        const val DESIGNATION = "DESIGNATION"
        const val LEAVE_ID = "LeaveID"
        const val SELECTED_TAB_EMP_OVERVIEW = "selected_tab_emp_overview"
        const val OWNEROVERVIEW = "owner_overview"
        const val EMP_ID = "EmpId"
        const val BOQ_ID = "boqID"
        const val CAT_ID = "cateID"
        const val ADMIN = "1"
        const val URGENT = "1"
        const val OTHER = "2"
        const val EMPLOYEEE = "2"
        const val VIEW = "ViewId"
        const val SalesLeadType = "1"
        const val Running = "1"
        const val Ongoing = "1"
        const val Hold = "2"
        const val Delay = "3"
        const val Completed = "4"
        const val Cancel = "5"
        const val Upcoming = "6"

        const val RunningVal = "Running"
        const val OngoingVal = "Ongoing"
        const val HoldVal = "Hold"
        const val DelayVal = "Delay"
        const val CompletedVal = "Completed"
        const val CancelVal = "Cancel"
        const val UpcomingVal = "Upcoming"
        const val SalesOMLeadType = "2"
        const val VIEW_D = "1"
        const val FOLLOWUP_ID = "FollowupId"
        const val DESIGNATION_ID = "designationID"
        const val DESIGNATION_NAME = "designationNAME"
        const val EMP_NAME = "empNAME"
        const val ReportDate = "reportDate"
        const val clientID = "clientID"
        const val workSheetID = "worksheetID"
        const val stageID = "stageID"
        const val labourId = "labourId"
        const val labourName = "labourName"
        const val tempAttandenceId = "tempAttandenceId"
        const val dailyreportID = "dailyreportID"
        const val checklist = "checklistId"
        const val checklist_TYpe = "checklist_Type"
        const val TrackingStatus = "0"
        const val reportID = " reportID"
        const val marketID = " marketId"
        const val marketType = " marketType"
        const val openTime = " openTime"
        const val showALl = " showAll"
        const val closeTime = " closeTime"
        const val clientName = "clientName"
        const val SELECT_TYPE = "selectType"
        const val SINGLE = "single"
        const val MODULE_NAME = "moduleName"
        const val CATEGORY_EMPLOYEE = "1"
        const val CATEGORY_ADD = "Add"
        const val CATEGORY_Edit = "Edit"
        const val CATEGORY_View = "View"
        const val CATEGORY_DELETE = "Delete"
        const val SLUG = "slug"
        const val SELECTED_DAY = "selectedDay"
        const val SELECTED_DAY_ID = "selectedDayID"
        const val COUPAN_CODE = "coupanCode"
        const val MONTH = "month"
        const val Category_Name = "category_name"
        const val MIN_LEAVE = "min"
        const val MAX_LEAVE = "max"
        const val IsCarry = "isCarry"
        const val IsPaid = "isPaid"
        const val ALL_SELECTED = "AllSelected"
        const val ADVANCE_SELECTED = "AdvanceSelected"
        const val SELECT_PROJECT_SITE = "Select Project Site"
        const val BASE_URL_VERSION = "v1.0/"
        const val BASE_URL_API = "api/"
        var mapGlobal: HashMap<String, String> = HashMap()
        var mapGlobalWeeksTemp: HashMap<String, String> = HashMap()
        var mapGlobalWeeks: HashMap<String, String> = HashMap()

        var liveUrl = "https://portal.codeurja.com/"
        var betaUrl = "http://solar.betablackboard.in/"
        var appConfigurationUrl = "api/app-configuration-detail"

        var currentUrl = ""

        //Permssion
        var featureLeaveList: HashMap<String, String> = HashMap()
        var leadListToAssign: HashMap<String, String> = HashMap()
        var schedulingAssignTeam: HashMap<String, String> = HashMap()
        var leadListToVisitAssign: HashMap<String, String> = HashMap()
        var scheduleListToVisitAssign: HashMap<String, String> = HashMap()
        var scheduleListToVisitTransfer: HashMap<String, String> = HashMap()
        var labourAttandenceCheck: HashMap<String, String> = HashMap()
        var labourOutAttandenceCheck: HashMap<String, String> = HashMap()
        var labourOutovertimeCheck: HashMap<String, String> = HashMap()
        var labourOuthoursCheck: HashMap<String, String> = HashMap()


        var leadListToTransfer: HashMap<String, String> = HashMap()
        var featureGlobal: HashMap<String, String> = HashMap()
        var featureGlobal1: HashMap<String, String> = HashMap()
        var featureEmpList: HashMap<String, String> = HashMap()

        private const val preferenceFile = "codeUrja"

        var temp0: HashMap<String, String> = HashMap()
        var temp1: HashMap<String, String> = HashMap()

    }

    interface SharedPref {
        companion object {

            const val FIREBASE_TOKEN = "FIREBASE_TOKEN"
             const val LOGGGED_IN = "LOGGGED_IN"
            const val IS_OWNER = "IS_OWNER"

            const val IS_BIOMETRIC_ACTIVE = "biometricActive"
            const val ENCRYPTED_PASS = "encryptedInfo"
            const val BIOMETRIC_EMAIL = "biometric_email"
            const val LANG = "LANG"
            const val LANG_Val = "LANG_Val"
            const val THEME = "THEME"
            const val EMAIL = "EMAIL"
            const val TXT_EMAIL = "TXTEMAIL"
            const val OTP = "OTP"
            const val ID = "ID_"
            const val COMPANY_ID = "COMPANY_ID"
            const val COMPANY_NAME = "COMPANY_NAME"
            const val TYPE_OWNER = "TYPE_OWNER"
            const val EMPLOYEE_ID = "EMPLOYEE_ID"
            const val MACHINE_DETAILS_ID = "MACHINE_DETAILS_ID"
            const val SERVICE_DETAILS_ID = "SERVICE_DETAILS_ID"
            const val MACHINE_PAYMENT_ID = "MACHINE_PAYMENT_ID"
            const val USER_ID = "USER_ID"
            var HAVE_PERMISSION: Boolean = false
            const val USER_TYPE = "USER_TYPE"
            const val EMP_OWNER = "EMP_OWNER"
            const val OWNER_ID = "OWNER_ID"
            const val USERPIN = "USERPIN"
            const val EMPOWNER_ID = "EMPOWNER_ID"
            const val RESIGTRATION_DATE = "RESIGTRATION_DATE"
            const val USER_NAME = "USER_NAME"
            const val USER_AVATAR = "USER_AVATAR"
            const val DEVICE_TOKEN = "DEVICE_TOKEN"
            const val DEVICE_ID = "DEVICE_ID"
            const val ACCESS_TOKEN = "ACCESS_TOKEN"

            const val SELF_ATTENDANCE = "SELF_ATTENDANCE"
            const val NOT_TOKEN = "NOT_TOKEN"
            const val DIAL_CODE = "DIAL_CODE"
            const val MOBILE = "MOBILE"
            const val SITE_ID = "SITE_ID"
            const val SITE_NAME = "SITE_NAME"
            const val SITE_OWNER_ID = "SITE_OWNER_ID"
            const val SITE_OWNER_FLAG = "SITE_OWNER_FLAG"
            const val START_DATE = "START_DATE"
            const val END_DATE = "END_DATE"
            //todo : required
            //budget get changed from long to float
            const val BUDGET = "BUDGET"
            const val SITENAME = "SITENAME"
            const val CANCEL_ID = "CANCEL_ID"
            const val REQUEST_ID = "REQUEST_ID"
            const val CURRENCY = "CURRENCY"
            const val SERVICE_TYPE = "SERVICE_TYPE"
            const val STRIPE_PUBLISHABLE_KEY = "STRIPE_PUBLISHABLE_KEY"
            const val LATITUDE = "LATITUDE"
            const val LONGITUDE = "LONGITUDE"
            const val PICTURE = "PICTURE"
            const val TOTAL_SITE = "TOTAL_SITE"
            const val IS_VERIFIED = "IS_VERIFIED"
            const val ROLES = "ROLES"
            const val IMAGE = "IMAGE"
            const val ROLE = "ROLE"
            const val FEATURE_ID = "FEATURE_ID"
            const val YOUR_OWNER_ID = "YOUR_OWNER_ID"
        }
    }
    private fun getInstance(context: Context): SharedPreferences? {
        if (instance == null) {
            instance = context.getSharedPreferences(preferenceFile, Context.MODE_PRIVATE)
        }
        return instance
    }

    interface LabourPayment {
        companion object {
            var Create: Boolean = true
            var View: Boolean = true
            var Delete: Boolean = true
            var Edit: Boolean = true
            var Print: Boolean = true
        }
    }

    interface LabourAttendance {
        companion object {
            var Create: Boolean = false
            var View: Boolean = false
            var Delete: Boolean = false
            var Edit: Boolean = false
            var Print: Boolean = false
        }
    }

    interface LabourWorksheet {
        companion object {
            var Create: Boolean = false
            var View: Boolean = false
            var Delete: Boolean = false
            var Edit: Boolean = false
            var Print: Boolean = false
        }
    }

    interface LabourDailyWork {
        companion object {
            var Create: Boolean = false
            var View: Boolean = false
            var Delete: Boolean = false
            var Edit: Boolean = false
            var Print: Boolean = false
        }
    }

    interface LabourReport {
        companion object {
            var Create: Boolean = false
            var View: Boolean = false
            var Delete: Boolean = false
            var Edit: Boolean = false
            var Print: Boolean = false
        }
    }

    interface LabourList {
        companion object {
            var Create: Boolean = false
            var View: Boolean = false
            var Delete: Boolean = false
            var Edit: Boolean = false
            var Print: Boolean = false
        }
    }

    interface LabourAdd {
        companion object {
            var Create: Boolean = false
            var View: Boolean = false
            var Delete: Boolean = false
            var Edit: Boolean = false
            var Print: Boolean = false
        }
    }

    interface EmployeeList {
        companion object {
            var Create: Boolean = false
            var View: Boolean = false
            var Delete: Boolean = false
            var Edit: Boolean = false
            var Print: Boolean = false
        }
    }

    interface Stock {
        companion object {
            var Create: Boolean = true
            var View: Boolean = true
            var Delete: Boolean = true
            var Edit: Boolean = true
            var Print: Boolean = true
        }
    }

    interface LabourReportCheck {
        companion object {
            var IsReport: Boolean = false
        }
    }

}