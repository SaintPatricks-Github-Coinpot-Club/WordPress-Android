package org.wordpress.android.analytics;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AnalyticsTracker {
    private static boolean mHasUserOptedOut;

    public static final String READER_DETAIL_TYPE_KEY = "post_detail_type";
    public static final String READER_DETAIL_TYPE_NORMAL = "normal";
    public static final String READER_DETAIL_TYPE_BLOG_PREVIEW = "preview-blog";
    public static final String READER_DETAIL_TYPE_TAG_PREVIEW = "preview-tag";
    public static final String ACTIVITY_LOG_ACTIVITY_ID_KEY = "activity_id";
    public static final String NOTIFICATIONS_SELECTED_FILTER = "selected_filter";

    public enum Stat {
        // This stat is part of a funnel that provides critical information.  Before
        // making ANY modification to this stat please refer to: p4qSXL-35X-p2
        APPLICATION_OPENED,
        APPLICATION_CLOSED,
        APPLICATION_INSTALLED,
        APPLICATION_UPGRADED,
        READER_ACCESSED,
        READER_ARTICLE_COMMENTED_ON,
        READER_ARTICLE_COMMENT_REPLIED_TO,
        READER_ARTICLE_COMMENTS_OPENED,
        READER_ARTICLE_COMMENT_LIKED,
        READER_ARTICLE_COMMENT_UNLIKED,
        READER_ARTICLE_LIKED,
        READER_ARTICLE_DETAIL_LIKED,
        READER_ARTICLE_REBLOGGED,
        READER_ARTICLE_DETAIL_REBLOGGED,
        READER_ARTICLE_OPENED,
        READER_ARTICLE_UNLIKED,
        READER_ARTICLE_DETAIL_UNLIKED,
        READER_ARTICLE_RENDERED,
        READER_ARTICLE_VISITED,
        READER_BLOG_BLOCKED,
        READER_BLOG_FOLLOWED,
        READER_BLOG_PREVIEWED,
        READER_BLOG_UNFOLLOWED,
        READER_SUGGESTED_SITE_VISITED,
        READER_SUGGESTED_SITE_TOGGLE_FOLLOW,
        READER_DISCOVER_VIEWED,
        READER_INFINITE_SCROLL,
        READER_LIST_FOLLOWED,
        READER_LIST_LOADED,
        READER_P2_SHOWN,
        READER_A8C_SHOWN,
        READER_LIST_PREVIEWED,
        READER_LIST_UNFOLLOWED,
        READER_TAG_FOLLOWED,
        READER_TAG_LOADED,
        READER_TAG_PREVIEWED,
        READER_TAG_UNFOLLOWED,
        READER_SEARCH_LOADED,
        READER_SEARCH_PERFORMED,
        READER_SEARCH_RESULT_TAPPED,
        READER_GLOBAL_RELATED_POST_CLICKED,
        READER_LOCAL_RELATED_POST_CLICKED,
        READER_VIEWPOST_INTERCEPTED,
        READER_BLOG_POST_INTERCEPTED,
        READER_FEED_POST_INTERCEPTED,
        READER_WPCOM_BLOG_POST_INTERCEPTED,
        READER_SIGN_IN_INITIATED,
        READER_WPCOM_SIGN_IN_NEEDED,
        READER_USER_UNAUTHORIZED,
        READER_POST_SAVED_FROM_OTHER_POST_LIST,
        READER_POST_SAVED_FROM_SAVED_POST_LIST,
        READER_POST_SAVED_FROM_DETAILS,
        READER_POST_UNSAVED_FROM_OTHER_POST_LIST,
        READER_POST_UNSAVED_FROM_SAVED_POST_LIST,
        READER_POST_UNSAVED_FROM_DETAILS,
        READER_SAVED_POST_OPENED_FROM_SAVED_POST_LIST,
        READER_SAVED_POST_OPENED_FROM_OTHER_POST_LIST,
        READER_SITE_SHARED,
        STATS_ACCESSED,
        STATS_ACCESS_ERROR,
        STATS_INSIGHTS_ACCESSED,
        STATS_INSIGHTS_MANAGEMENT_HINT_DISMISSED,
        STATS_INSIGHTS_MANAGEMENT_HINT_CLICKED,
        STATS_INSIGHTS_MANAGEMENT_ACCESSED,
        STATS_INSIGHTS_TYPE_MOVED_UP,
        STATS_INSIGHTS_TYPE_MOVED_DOWN,
        STATS_INSIGHTS_TYPE_REMOVED,
        STATS_INSIGHTS_MANAGEMENT_SAVED,
        STATS_INSIGHTS_MANAGEMENT_TYPE_ADDED,
        STATS_INSIGHTS_MANAGEMENT_TYPE_REMOVED,
        STATS_INSIGHTS_MANAGEMENT_TYPE_REORDERED,
        STATS_PERIOD_DAYS_ACCESSED,
        STATS_PERIOD_WEEKS_ACCESSED,
        STATS_PERIOD_MONTHS_ACCESSED,
        STATS_PERIOD_YEARS_ACCESSED,
        STATS_VIEW_ALL_ACCESSED,
        STATS_PREVIOUS_DATE_TAPPED,
        STATS_NEXT_DATE_TAPPED,
        STATS_FOLLOWERS_VIEW_MORE_TAPPED,
        STATS_TAGS_AND_CATEGORIES_VIEW_MORE_TAPPED,
        STATS_PUBLICIZE_VIEW_MORE_TAPPED,
        STATS_POSTS_AND_PAGES_VIEW_MORE_TAPPED,
        STATS_POSTS_AND_PAGES_ITEM_TAPPED,
        STATS_REFERRERS_VIEW_MORE_TAPPED,
        STATS_REFERRERS_ITEM_TAPPED,
        STATS_REFERRERS_ITEM_LONG_PRESSED,
        STATS_REFERRERS_ITEM_MARKED_AS_SPAM,
        STATS_REFERRERS_ITEM_MARKED_AS_NOT_SPAM,
        STATS_CLICKS_VIEW_MORE_TAPPED,
        STATS_COUNTRIES_VIEW_MORE_TAPPED,
        STATS_OVERVIEW_BAR_CHART_TAPPED,
        STATS_OVERVIEW_ERROR,
        STATS_VIDEO_PLAYS_VIEW_MORE_TAPPED,
        STATS_SEARCH_TERMS_VIEW_MORE_TAPPED,
        STATS_AUTHORS_VIEW_MORE_TAPPED,
        STATS_FILE_DOWNLOADS_VIEW_MORE_TAPPED,
        STATS_SINGLE_POST_ACCESSED,
        STATS_TAPPED_BAR_CHART,
        STATS_OVERVIEW_TYPE_TAPPED,
        STATS_SCROLLED_TO_BOTTOM,
        STATS_WIDGET_ADDED,
        STATS_WIDGET_REMOVED,
        STATS_WIDGET_TAPPED,
        STATS_LATEST_POST_SUMMARY_ADD_NEW_POST_TAPPED,
        STATS_LATEST_POST_SUMMARY_SHARE_POST_TAPPED,
        STATS_LATEST_POST_SUMMARY_VIEW_POST_DETAILS_TAPPED,
        STATS_LATEST_POST_SUMMARY_POST_ITEM_TAPPED,
        STATS_TAGS_AND_CATEGORIES_VIEW_TAG_TAPPED,
        STATS_AUTHORS_VIEW_POST_TAPPED,
        STATS_CLICKS_ITEM_TAPPED,
        STATS_VIDEO_PLAYS_VIDEO_TAPPED,
        STATS_DETAIL_POST_TAPPED,
        EDITOR_CREATED_POST,
        EDITOR_ADDED_PHOTO_VIA_DEVICE_LIBRARY,
        EDITOR_ADDED_VIDEO_VIA_DEVICE_LIBRARY,
        EDITOR_ADDED_PHOTO_VIA_MEDIA_EDITOR,
        EDITOR_ADDED_PHOTO_NEW,
        EDITOR_ADDED_VIDEO_NEW,
        EDITOR_ADDED_PHOTO_VIA_WP_MEDIA_LIBRARY,
        EDITOR_ADDED_VIDEO_VIA_WP_MEDIA_LIBRARY,
        EDITOR_ADDED_PHOTO_VIA_STOCK_MEDIA_LIBRARY,
        MEDIA_PHOTO_OPTIMIZED,
        MEDIA_PHOTO_OPTIMIZE_ERROR,
        MEDIA_VIDEO_OPTIMIZED,
        MEDIA_VIDEO_CANT_OPTIMIZE,
        MEDIA_VIDEO_OPTIMIZE_ERROR,
        MEDIA_PICKER_OPEN_CAPTURE_MEDIA,
        MEDIA_PICKER_OPEN_SYSTEM_PICKER,
        MEDIA_PICKER_OPEN_DEVICE_LIBRARY,
        MEDIA_PICKER_OPEN_WP_MEDIA,
        MEDIA_PICKER_OPEN_STOCK_LIBRARY,
        MEDIA_PICKER_OPEN_GIF_LIBRARY,
        MEDIA_PICKER_OPEN_WP_STORIES_CAPTURE,
        MEDIA_PICKER_OPEN_FOR_STORIES,
        MEDIA_PICKER_RECENT_MEDIA_SELECTED,
        MEDIA_PICKER_PREVIEW_OPENED,
        MEDIA_PICKER_SEARCH_EXPANDED,
        MEDIA_PICKER_SEARCH_COLLAPSED,
        MEDIA_PICKER_SEARCH_TRIGGERED,
        MEDIA_PICKER_SHOW_PERMISSIONS_SCREEN,
        MEDIA_PICKER_ITEM_SELECTED,
        MEDIA_PICKER_ITEM_UNSELECTED,
        MEDIA_PICKER_SELECTION_CLEARED,
        MEDIA_PICKER_OPENED,
        EDITOR_UPDATED_POST,
        EDITOR_SCHEDULED_POST,
        EDITOR_OPENED,
        POST_LIST_ACCESS_ERROR,
        POST_LIST_BUTTON_PRESSED,
        POST_LIST_ITEM_SELECTED,
        POST_LIST_AUTHOR_FILTER_CHANGED,
        POST_LIST_TAB_CHANGED,
        POST_LIST_VIEW_LAYOUT_TOGGLED,
        POST_LIST_SEARCH_ACCESSED,
        EDITOR_CLOSED,
        EDITOR_SESSION_START,
        EDITOR_SESSION_SWITCH_EDITOR,
        EDITOR_SESSION_TEMPLATE_APPLY,
        EDITOR_SESSION_END,
        EDITOR_PUBLISHED_POST,
        EDITOR_POST_PUBLISH_TAPPED,
        EDITOR_POST_SCHEDULE_CHANGED,
        EDITOR_POST_VISIBILITY_CHANGED,
        EDITOR_POST_TAGS_CHANGED,
        EDITOR_POST_PUBLISH_NOW_TAPPED,
        EDITOR_POST_PASSWORD_CHANGED,
        EDITOR_POST_CATEGORIES_ADDED,
        EDITOR_POST_FORMAT_CHANGED,
        EDITOR_POST_SLUG_CHANGED,
        EDITOR_POST_EXCERPT_CHANGED,
        EDITOR_SAVED_DRAFT,
        EDITOR_EDITED_IMAGE, // Visual editor only
        EDITOR_UPLOAD_MEDIA_FAILED, // Visual editor only
        EDITOR_UPLOAD_MEDIA_RETRIED, // Visual editor only
        EDITOR_TAPPED_BLOCKQUOTE,
        EDITOR_TAPPED_BOLD,
        EDITOR_TAPPED_ELLIPSIS_COLLAPSE,
        EDITOR_TAPPED_ELLIPSIS_EXPAND,
        EDITOR_TAPPED_HEADING,
        EDITOR_TAPPED_HEADING_1,
        EDITOR_TAPPED_HEADING_2,
        EDITOR_TAPPED_HEADING_3,
        EDITOR_TAPPED_HEADING_4,
        EDITOR_TAPPED_HEADING_5,
        EDITOR_TAPPED_HEADING_6,
        EDITOR_TAPPED_HTML, // Visual editor only
        EDITOR_TAPPED_HORIZONTAL_RULE,
        EDITOR_TAPPED_IMAGE,
        EDITOR_TAPPED_ITALIC,
        EDITOR_TAPPED_LINK_ADDED,
        EDITOR_TAPPED_LIST,
        EDITOR_TAPPED_LIST_ORDERED, // Visual editor only
        EDITOR_TAPPED_LIST_UNORDERED, // Visual editor only
        EDITOR_TAPPED_NEXT_PAGE,
        EDITOR_TAPPED_PARAGRAPH,
        EDITOR_TAPPED_PREFORMAT,
        EDITOR_TAPPED_READ_MORE,
        EDITOR_TAPPED_STRIKETHROUGH,
        EDITOR_TAPPED_UNDERLINE,
        EDITOR_TAPPED_ALIGN_LEFT,
        EDITOR_TAPPED_ALIGN_CENTER,
        EDITOR_TAPPED_ALIGN_RIGHT,
        EDITOR_TAPPED_REDO,
        EDITOR_TAPPED_UNDO,
        EDITOR_AZTEC_TOGGLED_OFF, // Aztec editor only
        EDITOR_AZTEC_TOGGLED_ON, // Aztec editor only
        EDITOR_AZTEC_ENABLED, // Aztec editor only
        EDITOR_GUTENBERG_ENABLED, // Gutenberg editor only
        EDITOR_GUTENBERG_DISABLED, // Gutenberg editor only
        EDITOR_HELP_SHOWN,
        EDITOR_SETTINGS_FETCHED,
        REVISIONS_LIST_VIEWED,
        REVISIONS_DETAIL_VIEWED_FROM_LIST,
        REVISIONS_DETAIL_VIEWED_FROM_SWIPE,
        REVISIONS_DETAIL_VIEWED_FROM_CHEVRON,
        REVISIONS_DETAIL_CANCELLED,
        REVISIONS_REVISION_LOADED,
        REVISIONS_LOAD_UNDONE,
        FOLLOWED_BLOG_NOTIFICATIONS_READER_ENABLED,
        FOLLOWED_BLOG_NOTIFICATIONS_READER_MENU_OFF,
        FOLLOWED_BLOG_NOTIFICATIONS_READER_MENU_ON,
        FOLLOWED_BLOG_NOTIFICATIONS_SETTINGS_OFF,
        FOLLOWED_BLOG_NOTIFICATIONS_SETTINGS_ON,
        FOLLOWED_BLOG_NOTIFICATIONS_SETTINGS_EMAIL_OFF,
        FOLLOWED_BLOG_NOTIFICATIONS_SETTINGS_EMAIL_ON,
        FOLLOWED_BLOG_NOTIFICATIONS_SETTINGS_EMAIL_INSTANTLY,
        FOLLOWED_BLOG_NOTIFICATIONS_SETTINGS_EMAIL_DAILY,
        FOLLOWED_BLOG_NOTIFICATIONS_SETTINGS_EMAIL_WEEKLY,
        FOLLOWED_BLOG_NOTIFICATIONS_SETTINGS_COMMENTS_OFF,
        FOLLOWED_BLOG_NOTIFICATIONS_SETTINGS_COMMENTS_ON,
        ME_ACCESSED,
        ME_GRAVATAR_TAPPED,
        ME_GRAVATAR_SHOT_NEW,
        ME_GRAVATAR_GALLERY_PICKED,
        ME_GRAVATAR_CROPPED,
        ME_GRAVATAR_UPLOADED,
        ME_GRAVATAR_UPLOAD_UNSUCCESSFUL,
        ME_GRAVATAR_UPLOAD_EXCEPTION,
        MY_SITE_ACCESSED,
        MY_SITE_ICON_TAPPED,
        MY_SITE_ICON_REMOVED,
        MY_SITE_ICON_SHOT_NEW,
        MY_SITE_ICON_GALLERY_PICKED,
        MY_SITE_ICON_CROPPED,
        MY_SITE_ICON_UPLOADED,
        MY_SITE_ICON_UPLOAD_UNSUCCESSFUL,
        NOTIFICATIONS_DISABLED,
        NOTIFICATIONS_ENABLED,
        NOTIFICATIONS_ACCESSED,
        NOTIFICATIONS_OPENED_NOTIFICATION_DETAILS,
        NOTIFICATIONS_MISSING_SYNC_WARNING,
        NOTIFICATION_REPLIED_TO,
        NOTIFICATION_QUICK_ACTIONS_REPLIED_TO,
        NOTIFICATION_APPROVED,
        NOTIFICATION_QUICK_ACTIONS_APPROVED,
        NOTIFICATION_UNAPPROVED,
        NOTIFICATION_LIKED,
        NOTIFICATION_QUICK_ACTIONS_LIKED,
        NOTIFICATION_QUICK_ACTIONS_QUICKACTION_TOUCHED,
        NOTIFICATION_UNLIKED,
        NOTIFICATION_TRASHED,
        NOTIFICATION_FLAGGED_AS_SPAM,
        NOTIFICATION_SWIPE_PAGE_CHANGED,
        NOTIFICATION_PENDING_DRAFTS_TAPPED,
        NOTIFICATION_PENDING_DRAFTS_IGNORED,
        NOTIFICATION_PENDING_DRAFTS_DISMISSED,
        NOTIFICATION_PENDING_DRAFTS_SETTINGS_ENABLED,
        NOTIFICATION_PENDING_DRAFTS_SETTINGS_DISABLED,
        NOTIFICATION_UPLOAD_MEDIA_SUCCESS_WRITE_POST,
        NOTIFICATION_UPLOAD_POST_ERROR_RETRY,
        NOTIFICATION_UPLOAD_MEDIA_ERROR_RETRY,
        NOTIFICATION_RECEIVED_PROCESSING_START,
        NOTIFICATION_RECEIVED_PROCESSING_END,
        NOTIFICATION_SHOWN,
        NOTIFICATION_TAPPED,
        NOTIFICATION_DISMISSED,
        OPENED_POSTS,
        OPENED_PAGES,
        OPENED_PAGE_PARENT,
        OPENED_COMMENTS,
        OPENED_VIEW_SITE,
        OPENED_VIEW_SITE_FROM_HEADER,
        OPENED_VIEW_ADMIN,
        OPENED_MEDIA_LIBRARY,
        OPENED_BLOG_SETTINGS,
        OPENED_ACCOUNT_SETTINGS,
        ACCOUNT_SETTINGS_CHANGE_USERNAME_SUCCEEDED,
        ACCOUNT_SETTINGS_CHANGE_USERNAME_FAILED,
        ACCOUNT_SETTINGS_CHANGE_USERNAME_SUGGESTIONS_FAILED,
        OPENED_APP_SETTINGS,
        OPENED_MY_PROFILE,
        OPENED_PEOPLE_MANAGEMENT,
        OPENED_PERSON,
        OPENED_PLUGIN_DIRECTORY,
        OPENED_PLANS,
        OPENED_PLANS_COMPARISON,
        OPENED_SHARING_MANAGEMENT,
        OPENED_SHARING_BUTTON_MANAGEMENT,
        ACTIVITY_LOG_LIST_OPENED,
        ACTIVITY_LOG_DETAIL_OPENED,
        ACTIVITY_LOG_REWIND_STARTED,
        ACTIVITY_LOG_FILTER_BAR_DATE_RANGE_BUTTON_TAPPED,
        ACTIVITY_LOG_FILTER_BAR_ACTIVITY_TYPE_BUTTON_TAPPED,
        ACTIVITY_LOG_FILTER_BAR_DATE_RANGE_SELECTED,
        ACTIVITY_LOG_FILTER_BAR_ACTIVITY_TYPE_SELECTED,
        ACTIVITY_LOG_FILTER_BAR_DATE_RANGE_RESET,
        ACTIVITY_LOG_FILTER_BAR_ACTIVITY_TYPE_RESET,
        JETPACK_BACKUP_LIST_OPENED,
        JETPACK_BACKUP_REWIND_STARTED,
        JETPACK_BACKUP_FILTER_BAR_DATE_RANGE_BUTTON_TAPPED,
        JETPACK_BACKUP_FILTER_BAR_DATE_RANGE_SELECTED,
        JETPACK_BACKUP_FILTER_BAR_DATE_RANGE_RESET,
        JETPACK_SCAN_ACCESSED,
        JETPACK_SCAN_HISTORY_ACCESSED,
        JETPACK_SCAN_HISTORY_FILTER,
        JETPACK_SCAN_THREAT_LIST_ITEM_TAPPED,
        JETPACK_SCAN_THREAT_CODEABLE_ESTIMATE_TAPPED,
        JETPACK_SCAN_RUN_TAPPED,
        JETPACK_SCAN_IGNORE_THREAT_DIALOG_OPEN,
        JETPACK_SCAN_THREAT_IGNORE_TAPPED,
        JETPACK_SCAN_FIX_THREAT_DIALOG_OPEN,
        JETPACK_SCAN_THREAT_FIX_TAPPED,
        JETPACK_SCAN_ALL_THREATS_OPEN,
        JETPACK_SCAN_ALL_THREATS_FIX_TAPPED,
        JETPACK_SCAN_ERROR,
        OPENED_PLUGIN_LIST,
        OPENED_PLUGIN_DETAIL,
        CREATE_ACCOUNT_INITIATED,
        CREATE_ACCOUNT_EMAIL_EXISTS,
        CREATE_ACCOUNT_USERNAME_EXISTS,
        CREATE_ACCOUNT_FAILED,
        // This stat is part of a funnel that provides critical information.  Before
        // making ANY modification to this stat please refer to: p4qSXL-35X-p2
        CREATED_ACCOUNT,
        ACCOUNT_LOGOUT,
        SHARED_ITEM,
        SHARED_ITEM_READER,
        ADDED_SELF_HOSTED_SITE,
        SIGNED_IN,
        SIGNED_INTO_JETPACK,
        INSTALL_JETPACK_SELECTED,
        INSTALL_JETPACK_CANCELLED,
        INSTALL_JETPACK_COMPLETED,
        INSTALL_JETPACK_REMOTE_START,
        INSTALL_JETPACK_REMOTE_COMPLETED,
        INSTALL_JETPACK_REMOTE_FAILED,
        INSTALL_JETPACK_REMOTE_CONNECT,
        INSTALL_JETPACK_REMOTE_LOGIN,
        INSTALL_JETPACK_REMOTE_RESTART,
        INSTALL_JETPACK_REMOTE_START_MANUAL_FLOW,
        INSTALL_JETPACK_REMOTE_ALREADY_INSTALLED,
        CONNECT_JETPACK_SELECTED,
        CONNECT_JETPACK_FAILED,
        PUSH_NOTIFICATION_RECEIVED,
        PUSH_NOTIFICATION_TAPPED, // Same of opened
        UNIFIED_LOGIN_STEP,
        UNIFIED_LOGIN_INTERACTION,
        UNIFIED_LOGIN_FAILURE,
        LOGIN_ACCESSED,
        LOGIN_MAGIC_LINK_EXITED,
        LOGIN_MAGIC_LINK_FAILED,
        LOGIN_MAGIC_LINK_OPENED,
        LOGIN_MAGIC_LINK_REQUESTED,
        LOGIN_MAGIC_LINK_SUCCEEDED,
        LOGIN_FAILED,
        LOGIN_FAILED_TO_GUESS_XMLRPC,
        LOGIN_INSERTED_INVALID_URL,
        LOGIN_AUTOFILL_CREDENTIALS_FILLED,
        LOGIN_AUTOFILL_CREDENTIALS_UPDATED,
        LOGIN_PROLOGUE_PAGED,
        LOGIN_PROLOGUE_PAGED_JETPACK,
        LOGIN_PROLOGUE_PAGED_NOTIFICATIONS,
        LOGIN_PROLOGUE_PAGED_POST,
        LOGIN_PROLOGUE_PAGED_READER,
        LOGIN_PROLOGUE_PAGED_STATS,
        LOGIN_PROLOGUE_VIEWED,
        LOGIN_EMAIL_FORM_VIEWED,
        LOGIN_MAGIC_LINK_OPEN_EMAIL_CLIENT_VIEWED,
        LOGIN_MAGIC_LINK_OPEN_EMAIL_CLIENT_CLICKED,
        LOGIN_MAGIC_LINK_REQUEST_FORM_VIEWED,
        LOGIN_PASSWORD_FORM_VIEWED,
        LOGIN_URL_FORM_VIEWED,
        LOGIN_URL_HELP_SCREEN_VIEWED,
        LOGIN_CONNECTED_SITE_INFO_REQUESTED,
        LOGIN_CONNECTED_SITE_INFO_FAILED,
        LOGIN_CONNECTED_SITE_INFO_SUCCEEDED,
        LOGIN_USERNAME_PASSWORD_FORM_VIEWED,
        LOGIN_TWO_FACTOR_FORM_VIEWED,
        LOGIN_EPILOGUE_VIEWED,
        LOGIN_FORGOT_PASSWORD_CLICKED,
        LOGIN_SOCIAL_BUTTON_CLICK,
        LOGIN_SOCIAL_BUTTON_FAILURE,
        LOGIN_SOCIAL_CONNECT_SUCCESS,
        LOGIN_SOCIAL_CONNECT_FAILURE,
        LOGIN_SOCIAL_SUCCESS,
        LOGIN_SOCIAL_FAILURE,
        LOGIN_SOCIAL_2FA_NEEDED,
        LOGIN_SOCIAL_ACCOUNTS_NEED_CONNECTING,
        LOGIN_SOCIAL_ERROR_UNKNOWN_USER,
        LOGIN_WPCOM_BACKGROUND_SERVICE_UPDATE,
        PAGES_SET_PARENT_CHANGES_SAVED,
        PAGES_ADD_PAGE,
        PAGES_TAB_PRESSED,
        PAGES_OPTIONS_PRESSED,
        PAGES_SEARCH_ACCESSED,
        // This stat is part of a funnel that provides critical information.  Before
        // making ANY modification to this stat please refer to: p4qSXL-35X-p2
        SIGNUP_BUTTON_TAPPED,
        SIGNUP_EMAIL_BUTTON_TAPPED,
        SIGNUP_EMAIL_EPILOGUE_GRAVATAR_CROPPED,
        SIGNUP_EMAIL_EPILOGUE_GRAVATAR_GALLERY_PICKED,
        SIGNUP_EMAIL_EPILOGUE_GRAVATAR_SHOT_NEW,
        SIGNUP_EMAIL_EPILOGUE_UNCHANGED,
        SIGNUP_EMAIL_EPILOGUE_UPDATE_DISPLAY_NAME_FAILED,
        SIGNUP_EMAIL_EPILOGUE_UPDATE_DISPLAY_NAME_SUCCEEDED,
        SIGNUP_EMAIL_EPILOGUE_UPDATE_USERNAME_FAILED,
        SIGNUP_EMAIL_EPILOGUE_UPDATE_USERNAME_SUCCEEDED,
        SIGNUP_EMAIL_EPILOGUE_USERNAME_SUGGESTIONS_FAILED,
        SIGNUP_EMAIL_EPILOGUE_USERNAME_TAPPED,
        SIGNUP_EMAIL_EPILOGUE_VIEWED,
        SIGNUP_SOCIAL_BUTTON_TAPPED,
        SIGNUP_TERMS_OF_SERVICE_TAPPED,
        SIGNUP_CANCELED,
        SIGNUP_EMAIL_TO_LOGIN,
        SIGNUP_MAGIC_LINK_FAILED,
        SIGNUP_MAGIC_LINK_OPENED,
        SIGNUP_MAGIC_LINK_OPEN_EMAIL_CLIENT_CLICKED,
        SIGNUP_MAGIC_LINK_SENT,
        SIGNUP_MAGIC_LINK_SUCCEEDED,
        SIGNUP_SOCIAL_ACCOUNTS_NEED_CONNECTING,
        SIGNUP_SOCIAL_BUTTON_FAILURE,
        SIGNUP_SOCIAL_EPILOGUE_UNCHANGED,
        SIGNUP_SOCIAL_EPILOGUE_UPDATE_DISPLAY_NAME_FAILED,
        SIGNUP_SOCIAL_EPILOGUE_UPDATE_DISPLAY_NAME_SUCCEEDED,
        SIGNUP_SOCIAL_EPILOGUE_UPDATE_USERNAME_FAILED,
        SIGNUP_SOCIAL_EPILOGUE_UPDATE_USERNAME_SUCCEEDED,
        SIGNUP_SOCIAL_EPILOGUE_USERNAME_SUGGESTIONS_FAILED,
        SIGNUP_SOCIAL_EPILOGUE_USERNAME_TAPPED,
        SIGNUP_SOCIAL_EPILOGUE_VIEWED,
        SIGNUP_SOCIAL_SUCCESS,
        SIGNUP_SOCIAL_TO_LOGIN,
        ENHANCED_SITE_CREATION_ACCESSED,
        ENHANCED_SITE_CREATION_SEGMENTS_VIEWED,
        ENHANCED_SITE_CREATION_SEGMENTS_SELECTED,
        ENHANCED_SITE_CREATION_DOMAINS_ACCESSED,
        ENHANCED_SITE_CREATION_DOMAINS_SELECTED,
        ENHANCED_SITE_CREATION_SUCCESS_LOADING,
        ENHANCED_SITE_CREATION_SUCCESS_PREVIEW_VIEWED,
        ENHANCED_SITE_CREATION_SUCCESS_PREVIEW_LOADED,
        ENHANCED_SITE_CREATION_PREVIEW_OK_BUTTON_TAPPED,
        ENHANCED_SITE_CREATION_EXITED,
        ENHANCED_SITE_CREATION_ERROR_SHOWN,
        ENHANCED_SITE_CREATION_BACKGROUND_SERVICE_UPDATED,
        ENHANCED_SITE_CREATION_SITE_DESIGN_VIEWED,
        ENHANCED_SITE_CREATION_SITE_DESIGN_THUMBNAIL_MODE_BUTTON_TAPPED,
        ENHANCED_SITE_CREATION_SITE_DESIGN_SELECTED,
        ENHANCED_SITE_CREATION_SITE_DESIGN_SKIPPED,
        ENHANCED_SITE_CREATION_SITE_DESIGN_PREVIEW_VIEWED,
        ENHANCED_SITE_CREATION_SITE_DESIGN_PREVIEW_MODE_BUTTON_TAPPED,
        ENHANCED_SITE_CREATION_SITE_DESIGN_PREVIEW_MODE_CHANGED,
        ENHANCED_SITE_CREATION_SITE_DESIGN_PREVIEW_LOADING,
        ENHANCED_SITE_CREATION_SITE_DESIGN_PREVIEW_LOADED,
        LAYOUT_PICKER_PREVIEW_MODE_CHANGED,
        LAYOUT_PICKER_THUMBNAIL_MODE_BUTTON_TAPPED,
        LAYOUT_PICKER_PREVIEW_MODE_BUTTON_TAPPED,
        LAYOUT_PICKER_PREVIEW_LOADING,
        LAYOUT_PICKER_PREVIEW_LOADED,
        LAYOUT_PICKER_PREVIEW_VIEWED,
        LAYOUT_PICKER_ERROR_SHOWN,
        CATEGORY_FILTER_SELECTED,
        CATEGORY_FILTER_DESELECTED,
        // This stat is part of a funnel that provides critical information.  Before
        // making ANY modification to this stat please refer to: p4qSXL-35X-p2
        SITE_CREATED,
        MEDIA_LIBRARY_ADDED_PHOTO,
        MEDIA_LIBRARY_ADDED_VIDEO,
        PERSON_REMOVED,
        PERSON_UPDATED,
        PUSH_AUTHENTICATION_APPROVED,
        PUSH_AUTHENTICATION_EXPIRED,
        PUSH_AUTHENTICATION_FAILED,
        PUSH_AUTHENTICATION_IGNORED,
        NOTIFICATION_SETTINGS_LIST_OPENED,
        NOTIFICATION_SETTINGS_STREAMS_OPENED,
        NOTIFICATION_SETTINGS_DETAILS_OPENED,
        NOTIFICATION_SETTINGS_APP_NOTIFICATIONS_DISABLED,
        NOTIFICATION_SETTINGS_APP_NOTIFICATIONS_ENABLED,
        NOTIFICATION_TAPPED_SEGMENTED_CONTROL,
        THEMES_ACCESSED_THEMES_BROWSER,
        THEMES_ACCESSED_SEARCH,
        THEMES_CHANGED_THEME,
        THEMES_PREVIEWED_SITE,
        THEMES_DEMO_ACCESSED,
        THEMES_CUSTOMIZE_ACCESSED,
        THEMES_SUPPORT_ACCESSED,
        THEMES_DETAILS_ACCESSED,
        ACCOUNT_SETTINGS_LANGUAGE_CHANGED,
        SITE_SETTINGS_ACCESSED,
        SITE_SETTINGS_ACCESSED_MORE_SETTINGS,
        SITE_SETTINGS_LEARN_MORE_CLICKED,
        SITE_SETTINGS_LEARN_MORE_LOADED,
        SITE_SETTINGS_ADDED_LIST_ITEM,
        SITE_SETTINGS_DELETED_LIST_ITEMS,
        SITE_SETTINGS_SAVED_REMOTELY,
        SITE_SETTINGS_HINT_TOAST_SHOWN,
        SITE_SETTINGS_START_OVER_ACCESSED,
        SITE_SETTINGS_START_OVER_CONTACT_SUPPORT_CLICKED,
        SITE_SETTINGS_EXPORT_SITE_ACCESSED,
        SITE_SETTINGS_EXPORT_SITE_REQUESTED,
        SITE_SETTINGS_EXPORT_SITE_RESPONSE_OK,
        SITE_SETTINGS_EXPORT_SITE_RESPONSE_ERROR,
        SITE_SETTINGS_DELETE_SITE_ACCESSED,
        SITE_SETTINGS_DELETE_SITE_PURCHASES_REQUESTED,
        SITE_SETTINGS_DELETE_SITE_PURCHASES_SHOWN,
        SITE_SETTINGS_DELETE_SITE_PURCHASES_SHOW_CLICKED,
        SITE_SETTINGS_DELETE_SITE_REQUESTED,
        SITE_SETTINGS_DELETE_SITE_RESPONSE_OK,
        SITE_SETTINGS_DELETE_SITE_RESPONSE_ERROR,
        SITE_SETTINGS_OPTIMIZE_IMAGES_CHANGED,
        SITE_SETTINGS_JETPACK_SECURITY_SETTINGS_VIEWED,
        SITE_SETTINGS_JETPACK_ALLOWLISTED_IPS_VIEWED,
        SITE_SETTINGS_JETPACK_ALLOWLISTED_IPS_CHANGED,
        ABTEST_START,
        FEATURE_FLAG_VALUE,
        FEATURE_FLAGS_SYNCED_STATE,
        EXPERIMENT_VARIANT_SET,
        TRAIN_TRACKS_RENDER,
        TRAIN_TRACKS_INTERACT,
        DEEP_LINKED,
        DEEP_LINKED_FALLBACK,
        DEEP_LINK_NOT_DEFAULT_HANDLER,
        MEDIA_UPLOAD_STARTED,
        MEDIA_UPLOAD_ERROR,
        MEDIA_UPLOAD_SUCCESS,
        MEDIA_UPLOAD_CANCELED,
        APP_PERMISSION_GRANTED,
        APP_PERMISSION_DENIED,
        SHARE_TO_WP_SUCCEEDED,
        PLUGIN_ACTIVATED,
        PLUGIN_AUTOUPDATE_ENABLED,
        PLUGIN_AUTOUPDATE_DISABLED,
        PLUGIN_DEACTIVATED,
        PLUGIN_INSTALLED,
        PLUGIN_REMOVED,
        PLUGIN_SEARCH_PERFORMED,
        PLUGIN_UPDATED,
        STOCK_MEDIA_ACCESSED,
        STOCK_MEDIA_SEARCHED,
        STOCK_MEDIA_UPLOADED,
        GIF_PICKER_SEARCHED,
        GIF_PICKER_ACCESSED,
        GIF_PICKER_DOWNLOADED,
        SHORTCUT_STATS_CLICKED,
        SHORTCUT_NOTIFICATIONS_CLICKED,
        SHORTCUT_NEW_POST_CLICKED,
        AUTOMATED_TRANSFER_CONFIRM_DIALOG_SHOWN,
        AUTOMATED_TRANSFER_CONFIRM_DIALOG_CANCELLED,
        AUTOMATED_TRANSFER_CHECK_ELIGIBILITY,
        AUTOMATED_TRANSFER_NOT_ELIGIBLE,
        AUTOMATED_TRANSFER_INITIATE,
        AUTOMATED_TRANSFER_INITIATED,
        AUTOMATED_TRANSFER_INITIATION_FAILED,
        AUTOMATED_TRANSFER_STATUS_COMPLETE,
        AUTOMATED_TRANSFER_STATUS_FAILED,
        AUTOMATED_TRANSFER_FLOW_COMPLETE,
        AUTOMATED_TRANSFER_CUSTOM_DOMAIN_PURCHASED,
        AUTOMATED_TRANSFER_CUSTOM_DOMAIN_PURCHASE_FAILED,
        PUBLICIZE_SERVICE_CONNECTED,
        PUBLICIZE_SERVICE_DISCONNECTED,
        SUPPORT_OPENED,
        SUPPORT_HELP_CENTER_VIEWED,
        SUPPORT_NEW_REQUEST_VIEWED,
        SUPPORT_TICKET_LIST_VIEWED,
        SUPPORT_IDENTITY_FORM_VIEWED,
        SUPPORT_IDENTITY_SET,
        QUICK_START_STARTED,
        QUICK_START_TASK_DIALOG_VIEWED,
        QUICK_START_TASK_DIALOG_NEGATIVE_TAPPED,
        QUICK_START_TASK_DIALOG_POSITIVE_TAPPED,
        QUICK_START_REMOVE_DIALOG_NEGATIVE_TAPPED,
        QUICK_START_REMOVE_DIALOG_POSITIVE_TAPPED,
        QUICK_START_TYPE_CUSTOMIZE_VIEWED,
        QUICK_START_TYPE_GROW_VIEWED,
        QUICK_START_TYPE_CUSTOMIZE_DISMISSED,
        QUICK_START_TYPE_GROW_DISMISSED,
        QUICK_START_LIST_CUSTOMIZE_COLLAPSED,
        QUICK_START_LIST_GROW_COLLAPSED,
        QUICK_START_LIST_CUSTOMIZE_EXPANDED,
        QUICK_START_LIST_GROW_EXPANDED,
        QUICK_START_LIST_CREATE_SITE_SKIPPED,
        QUICK_START_LIST_UPDATE_SITE_TITLE_SKIPPED,
        QUICK_START_LIST_VIEW_SITE_SKIPPED,
        QUICK_START_LIST_ADD_SOCIAL_SKIPPED,
        QUICK_START_LIST_PUBLISH_POST_SKIPPED,
        QUICK_START_LIST_FOLLOW_SITE_SKIPPED,
        QUICK_START_LIST_UPLOAD_ICON_SKIPPED,
        QUICK_START_LIST_CHECK_STATS_SKIPPED,
        QUICK_START_LIST_EXPLORE_PLANS_SKIPPED,
        QUICK_START_LIST_EDIT_HOMEPAGE_SKIPPED,
        QUICK_START_LIST_REVIEW_PAGES_SKIPPED,
        QUICK_START_LIST_CREATE_SITE_TAPPED,
        QUICK_START_LIST_UPDATE_SITE_TITLE_TAPPED,
        QUICK_START_LIST_VIEW_SITE_TAPPED,
        QUICK_START_LIST_ADD_SOCIAL_TAPPED,
        QUICK_START_LIST_PUBLISH_POST_TAPPED,
        QUICK_START_LIST_FOLLOW_SITE_TAPPED,
        QUICK_START_LIST_UPLOAD_ICON_TAPPED,
        QUICK_START_LIST_CHECK_STATS_TAPPED,
        QUICK_START_LIST_EXPLORE_PLANS_TAPPED,
        QUICK_START_LIST_EDIT_HOMEPAGE_TAPPED,
        QUICK_START_LIST_REVIEW_PAGES_TAPPED,
        QUICK_START_CREATE_SITE_TASK_COMPLETED,
        QUICK_START_UPDATE_SITE_TITLE_COMPLETED,
        QUICK_START_VIEW_SITE_TASK_COMPLETED,
        QUICK_START_SHARE_SITE_TASK_COMPLETED,
        QUICK_START_PUBLISH_POST_TASK_COMPLETED,
        QUICK_START_FOLLOW_SITE_TASK_COMPLETED,
        QUICK_START_UPLOAD_ICON_COMPLETED,
        QUICK_START_CHECK_STATS_COMPLETED,
        QUICK_START_EXPLORE_PLANS_COMPLETED,
        QUICK_START_EDIT_HOMEPAGE_TASK_COMPLETED,
        QUICK_START_REVIEW_PAGES_TASK_COMPLETED,
        QUICK_START_ALL_TASKS_COMPLETED,
        QUICK_START_REQUEST_VIEWED,
        QUICK_START_REQUEST_DIALOG_NEGATIVE_TAPPED,
        QUICK_START_REQUEST_DIALOG_POSITIVE_TAPPED,
        QUICK_START_REQUEST_DIALOG_NEUTRAL_TAPPED,
        QUICK_START_NOTIFICATION_DISMISSED,
        QUICK_START_NOTIFICATION_SENT,
        QUICK_START_NOTIFICATION_TAPPED,
        QUICK_START_HIDE_CARD_TAPPED,
        QUICK_START_REMOVE_CARD_TAPPED,
        INSTALLATION_REFERRER_OBTAINED,
        INSTALLATION_REFERRER_FAILED,
        GUTENBERG_WARNING_CONFIRM_DIALOG_SHOWN,
        GUTENBERG_WARNING_CONFIRM_DIALOG_YES_TAPPED,
        GUTENBERG_WARNING_CONFIRM_DIALOG_CANCEL_TAPPED,
        GUTENBERG_WARNING_CONFIRM_DIALOG_DONT_SHOW_AGAIN_CHECKED,
        GUTENBERG_WARNING_CONFIRM_DIALOG_DONT_SHOW_AGAIN_UNCHECKED,
        GUTENBERG_WARNING_CONFIRM_DIALOG_LEARN_MORE_TAPPED,
        APP_REVIEWS_SAW_PROMPT,
        APP_REVIEWS_CANCELLED_PROMPT,
        APP_REVIEWS_RATED_APP,
        APP_REVIEWS_DECLINED_TO_RATE_APP,
        APP_REVIEWS_DECIDED_TO_RATE_LATER,
        APP_REVIEWS_EVENT_INCREMENTED_BY_UPLOADING_MEDIA,
        APP_REVIEWS_EVENT_INCREMENTED_BY_CHECKING_NOTIFICATION,
        APP_REVIEWS_EVENT_INCREMENTED_BY_PUBLISHING_POST_OR_PAGE,
        APP_REVIEWS_EVENT_INCREMENTED_BY_OPENING_READER_POST,
        DOMAIN_CREDIT_PROMPT_SHOWN,
        DOMAIN_CREDIT_REDEMPTION_TAPPED,
        DOMAIN_CREDIT_REDEMPTION_SUCCESS,
        DOMAIN_CREDIT_SUGGESTION_QUERIED,
        DOMAIN_CREDIT_NAME_SELECTED,
        QUICK_ACTION_STATS_TAPPED,
        QUICK_ACTION_PAGES_TAPPED,
        QUICK_ACTION_POSTS_TAPPED,
        QUICK_ACTION_MEDIA_TAPPED,
        AUTO_UPLOAD_POST_INVOKED,
        AUTO_UPLOAD_PAGE_INVOKED,
        UNPUBLISHED_REVISION_DIALOG_SHOWN,
        UNPUBLISHED_REVISION_DIALOG_LOAD_LOCAL_VERSION_CLICKED,
        UNPUBLISHED_REVISION_DIALOG_LOAD_UNPUBLISHED_VERSION_CLICKED,
        WELCOME_NO_SITES_INTERSTITIAL_SHOWN,
        WELCOME_NO_SITES_INTERSTITIAL_CREATE_NEW_SITE_TAPPED,
        WELCOME_NO_SITES_INTERSTITIAL_ADD_SELF_HOSTED_SITE_TAPPED,
        WELCOME_NO_SITES_INTERSTITIAL_DISMISSED,
        FEATURED_IMAGE_SET_CLICKED_POST_SETTINGS,
        FEATURED_IMAGE_PICKED_POST_SETTINGS,
        FEATURED_IMAGE_PICKED_GUTENBERG_EDITOR,
        FEATURED_IMAGE_REMOVED_GUTENBERG_EDITOR,
        FEATURED_IMAGE_UPLOAD_CANCELED_POST_SETTINGS,
        FEATURED_IMAGE_UPLOAD_RETRY_CLICKED_POST_SETTINGS,
        FEATURED_IMAGE_REMOVE_CLICKED_POST_SETTINGS,
        MEDIA_EDITOR_SHOWN,
        MEDIA_EDITOR_USED,
        STORY_SAVE_SUCCESSFUL,
        STORY_SAVE_ERROR,
        STORY_POST_SAVE_LOCALLY,
        STORY_POST_SAVE_REMOTELY,
        STORY_SAVE_ERROR_SNACKBAR_MANAGE_TAPPED,
        STORY_POST_PUBLISH_TAPPED,
        STORY_TEXT_CHANGED,
        STORY_INTRO_SHOWN,
        STORY_INTRO_DISMISSED,
        STORY_INTRO_CREATE_STORY_BUTTON_TAPPED,
        STORY_BLOCK_ADD_MEDIA_TAPPED,
        PREPUBLISHING_BOTTOM_SHEET_OPENED,
        PREPUBLISHING_BOTTOM_SHEET_DISMISSED,
        FEATURE_ANNOUNCEMENT_SHOWN_ON_APP_UPGRADE,
        FEATURE_ANNOUNCEMENT_SHOWN_FROM_APP_SETTINGS,
        FEATURE_ANNOUNCEMENT_FIND_OUT_MORE_TAPPED,
        FEATURE_ANNOUNCEMENT_CLOSE_DIALOG_BUTTON_TAPPED,
        PAGES_LIST_AUTHOR_FILTER_CHANGED,
        EDITOR_GUTENBERG_UNSUPPORTED_BLOCK_WEBVIEW_SHOWN,
        EDITOR_GUTENBERG_UNSUPPORTED_BLOCK_WEBVIEW_CLOSED,
        SELECT_INTERESTS_SHOWN,
        SELECT_INTERESTS_PICKED,
        READER_FOLLOWING_SHOWN,
        READER_LIKED_SHOWN,
        READER_SAVED_LIST_SHOWN,
        READER_CUSTOM_TAB_SHOWN,
        READER_DISCOVER_SHOWN,
        READER_DISCOVER_PAGINATED,
        READER_DISCOVER_TOPIC_TAPPED,
        READER_POST_CARD_TAPPED,
        READER_PULL_TO_REFRESH,
        POST_CARD_MORE_TAPPED,
        READER_ARTICLE_DETAIL_MORE_TAPPED,
        READER_CHIPS_MORE_TOGGLED,
        ENCRYPTED_LOGGING_UPLOAD_SUCCESSFUL,
        ENCRYPTED_LOGGING_UPLOAD_FAILED,
        READER_POST_REPORTED,
        READER_POST_MARKED_AS_SEEN,
        READER_POST_MARKED_AS_UNSEEN,
        SUGGESTION_SESSION_FINISHED,
        COMMENT_APPROVED,
        COMMENT_UNAPPROVED,
        COMMENT_SPAMMED,
        COMMENT_UNSPAMMED,
        COMMENT_LIKED,
        COMMENT_UNLIKED,
        COMMENT_TRASHED,
        COMMENT_UNTRASHED,
        COMMENT_REPLIED_TO,
        COMMENT_EDITED,
        COMMENT_VIEWED,
        COMMENT_DELETED,
        COMMENT_QUICK_ACTION_APPROVED,
        COMMENT_QUICK_ACTION_LIKED,
        COMMENT_QUICK_ACTION_REPLIED_TO,
        COMMENT_FOLLOW_CONVERSATION,
        COMMENT_BATCH_APPROVED,
        COMMENT_BATCH_UNAPPROVED,
        COMMENT_BATCH_SPAMMED,
        COMMENT_BATCH_TRASHED,
        COMMENT_BATCH_DELETED,
        COMMENT_EDITOR_OPENED,
        COMMENT_FILTER_CHANGED,
        JETPACK_RESTORE_OPENED,
        JETPACK_RESTORE_CONFIRMED,
        JETPACK_RESTORE_ERROR,
        JETPACK_BACKUP_DOWNLOAD_OPENED,
        JETPACK_BACKUP_DOWNLOAD_CONFIRMED,
        JETPACK_BACKUP_DOWNLOAD_ERROR,
        JETPACK_BACKUP_DOWNLOAD_FILE_DOWNLOAD_TAPPED,
        JETPACK_BACKUP_DOWNLOAD_SHARE_LINK_TAPPED,
        MY_SITE_CREATE_SHEET_SHOWN,
        MY_SITE_CREATE_SHEET_ACTION_TAPPED,
        POST_LIST_CREATE_SHEET_SHOWN,
        POST_LIST_CREATE_SHEET_ACTION_TAPPED,
        INVITE_LINKS_GET_STATUS,
        INVITE_LINKS_GENERATE,
        INVITE_LINKS_DISABLE,
        INVITE_LINKS_SHARE,
        JETPACK_BACKUP_DOWNLOAD_FILE_NOTICE_DOWNLOAD_TAPPED,
        JETPACK_BACKUP_DOWNLOAD_FILE_NOTICE_DISMISSED_TAPPED,
        ACTIVITY_LOG_DOWNLOAD_FILE_NOTICE_DOWNLOAD_TAPPED,
        ACTIVITY_LOG_DOWNLOAD_FILE_NOTICE_DISMISSED_TAPPED,
        USER_PROFILE_SHEET_SHOWN,
        USER_PROFILE_SHEET_SITE_SHOWN,
        BLOG_URL_PREVIEWED,
        LIKE_LIST_OPENED,
        LIKE_LIST_FETCHED_MORE,
        STORAGE_WARNING_SHOWN,
        STORAGE_WARNING_ACKNOWLEDGED,
        STORAGE_WARNING_CANCELED,
        STORAGE_WARNING_DONT_SHOW_AGAIN,
        BLOGGING_REMINDERS_SCREEN_SHOWN,
        BLOGGING_REMINDERS_BUTTON_PRESSED,
        BLOGGING_REMINDERS_FLOW_START,
        BLOGGING_REMINDERS_FLOW_DISMISSED,
        BLOGGING_REMINDERS_FLOW_COMPLETED,
        BLOGGING_REMINDERS_SCHEDULED,
        BLOGGING_REMINDERS_CANCELLED,
        BLOGGING_REMINDERS_NOTIFICATION_RECEIVED,
        LOGIN_EPILOGUE_CHOOSE_SITE_TAPPED,
        LOGIN_EPILOGUE_CREATE_NEW_SITE_TAPPED,
        CREATE_SITE_NOTIFICATION_SCHEDULED
    }

    private static final List<Tracker> TRACKERS = new ArrayList<>();

    private AnalyticsTracker() {
    }

    public static void init(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        boolean hasUserOptedOut = !prefs.getBoolean("wp_pref_send_usage_stats", true);
        if (hasUserOptedOut != mHasUserOptedOut) {
            mHasUserOptedOut = hasUserOptedOut;
        }
    }

    public static void setHasUserOptedOut(boolean hasUserOptedOut) {
        if (hasUserOptedOut != mHasUserOptedOut) {
            mHasUserOptedOut = hasUserOptedOut;
        }
    }

    public static void registerTracker(Tracker tracker) {
        if (tracker != null) {
            TRACKERS.add(tracker);
        }
    }

    public static void track(Stat stat) {
        if (mHasUserOptedOut) {
            return;
        }
        for (Tracker tracker : TRACKERS) {
            tracker.track(stat);
        }
    }

    public static void track(Stat stat, Map<String, ?> properties) {
        if (mHasUserOptedOut) {
            return;
        }
        for (Tracker tracker : TRACKERS) {
            tracker.track(stat, properties);
        }
    }

    /**
     * A convenience method for logging an error event with some additional meta data.
     * @param stat The stat to track.
     * @param errorContext A string providing additional context (if any) about the error.
     * @param errorType The type of error.
     * @param errorDescription The error text or other description.
     */
    public static void track(Stat stat, String errorContext, String errorType, String errorDescription) {
        Map<String, String> props = new HashMap<>();
        props.put("error_context", errorContext);
        props.put("error_type", errorType);
        props.put("error_description", errorDescription);
        track(stat, props);
    }

    public static void flush() {
        if (mHasUserOptedOut) {
            return;
        }
        for (Tracker tracker : TRACKERS) {
            tracker.flush();
        }
    }

    public static void endSession(boolean force) {
        if (mHasUserOptedOut && !force) {
            return;
        }
        for (Tracker tracker : TRACKERS) {
            tracker.endSession();
        }
    }

    public static void clearAllData() {
        for (Tracker tracker : TRACKERS) {
            tracker.clearAllData();
        }
    }

    public static void refreshMetadata(AnalyticsMetadata metadata) {
        for (Tracker tracker : TRACKERS) {
            tracker.refreshMetadata(metadata);
        }
    }
}
