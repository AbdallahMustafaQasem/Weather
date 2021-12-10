package abd.qasem.weather.domain.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class ResponseState : Parcelable {

    @Parcelize
    object Success : ResponseState()

    @Parcelize
    //Bad Request Errors HTTP 400
    class BadRequest(val state: BadRequestState) : ResponseState()

    @Parcelize
    // Unauthorized Errors HTTP 401
    class Unauthorized(val state: UnauthorizedState) : ResponseState()

    @Parcelize
    // Forbidden Errors HTTP 403
    class Forbidden(val state: ForbiddenState) : ResponseState()

    @Parcelize
    // Not Found Errors HTTP 404
    class NotFound(val state: NotFoundState) : ResponseState()

    @Parcelize
    // Server Errors HTTP 500
    class ServiceError(val state: ServiceErrorState) : ResponseState()

    @Parcelize
    //Service Unavailable Errors HTTP 503
    object ServiceUnavailable : ResponseState()

    @Parcelize
    object NetworkError : ResponseState()

    @Parcelize
    object GeneralError : ResponseState()

    @Parcelize
    object CannotConnectToServiceError : ResponseState()

}

enum class ServiceErrorState {
    InternalError,  // server error with sub error code =0
    Failed,
    Throttled,  // server error with sub error code =2 (show when request transactions api mor 1 time in 1 min)
    Timeout //server error with sub error code =3 (when a timeout occurs in creating or buying a space)
}

enum class BadRequestState {
    MissingParameters,  // client error with sub error code = 0
    InvalidParameters,  // client error with sub error code = 1
    InvalidOperation,  // client error with sub error code = 2
    InvalidSignUp,  // client error with sub error code = 3 (User Exist)
    InvalidWalletAddress,  // client error with sub error code = 4 (Wallet Address Not Correct)
    DuplicateWalletAddress,  //client error with sub error code = 5 (Duplicate Wallet Address)
    SpacePopulated,  //client error with sub error code = 6 (Space already taken)
    NoCredit,  //client error with sub error code = 7 (when user buy space but tokens < space price)
    InvalidPurchase,  //client error with sub error code = 8 (Fake Purchase)
    DuplicatePurchase,  //client error with sub error code = 9 (This Purchase find in DB)
    InvalidPassword,  //client error with sub error code = 10 (This return in Update Password )
    SpacePriceChanged,  //client error with sub error code = 11 (This return in Buy space)
    WithdrawMaxLimit,  //client error with sub error code = 12 (Withdraw more than 200,000)
    InvalidInvitationCode,  //client error with sub error code = 13 (redeemed invitation code not valid)
    BlockLimitExceeded,  //client error with sub error code = 14 (the user reached the maximum number of spaces that he can buy in a certain block)
    GlobalLimitExceeded,  //client error with sub error code = 15 (the user reached the maximum number of spaces that he can buy)
    UniqueSpaceNameLimitExceeded,  //client error with sub error code = 16 (User don't have a limit for new Space Name )
    SpaceNameAlreadyUsed,  //client error with sub error code = 17 (Space Name Used)
    MinimumWithdrawalLimit,  //client error with sub error code = 18 (Withdraw less than 20,000)
    SMSLimitExceeded,  //client error with sub error code = 19 (SMS verification limit exceeded)
    UserHasNoSpaces,  // client error with sub error code = 20
    ChatAttachmentSizeIncorrect,  // client error with sub error code = 21 (when the total size of parts is not equal for filesize header)
    InvalidMultipartUploadId,  // client error with sub error code = 22 (will be returned for the expired upload IDs in chunks case in message attachment)
    GWSpacesLimitExceeded,  // client error with sub error code = 23 (will be returned if user tried to create more than 5 spaces in grey world)
    NicknameCannotBeChanged,  // client error with sub error code = 24 (nickname change request limit exceeded within 2 weeks)
    AttachmentAlreadyUploaded,  // client error with sub error code = 25 (if tried to upload an already uploaded attachment in a post but not all attachments are uploaded)
    PostAlreadyCompleted,  // client error with sub error code = 26 (if tried to upload an already uploaded attachment in a post but all attachments are uploaded)
    AccountDeleted,  // user use email  or phone for account deleted = 27
    MaxBoughtSpaces,  // When max bought spaces names is reached an error code = 28
    UN_KNOWN_ERROR_29,
    UN_KNOWN_ERROR_30,
    UN_KNOWN_ERROR_31,
    UN_KNOWN_ERROR_32,
    InvalidTokenVersion,
    TokenPriceChanged,
    InvalidPurchaseTokensOrder,
    InvalidSocialSignUp,
    UN_KNOWN_ERROR_37,
    UN_KNOWN_ERROR_38,
    UN_KNOWN_ERROR_39,
    UN_KNOWN_ERROR_40,
    UN_KNOWN_ERROR_41,
    SpaceResetTimeLimitViolated, // 42
    UN_KNOWN_ERROR_43,
    PostUnderReset, //44
}

enum class UnauthorizedState {
    NotAuthenticated,  // token for user not valid sub error code =0
    AuthExpired,  // token for user not valid sub error code =1
    TokenInvalidated,  // token for user not valid sub error code =2
    OldBuild,  // token for user not valid sub error code =3 ( Force Update )
    InvalidRefreshToken,  // Refresh token for user not valid sub error code =4
    INVALID_ACCESS_TOKEN,  // access token for user not valid sub error code =5
    ChatInvalidRefreshToken // Chat Refresh token for user not valid sub error code =6
}

enum class ForbiddenState {
    NoPermissions,  // user don't have Permissions sub error code =0
    NotVerified,  // user not confirm his email so should blocked this user sub error code =1
    GiftAlreadyClaimed,  // user try to claim a daily gift that already he had claimed it = 2
    GiftExpired,  // user try to claim a daily gift that is expired = 3
    ProfileNotVerified,  // user account is not verified = 4
    InvalidKYC, // user didn't apply to KYC = 5
    UserBlockedFromCommenting
}

enum class NotFoundState {
    FileNotFound,  // user input not supported file sub error code =0
    AuthConflict,  // input error when phone number without country code and found in server for 2 users sub error code =1
    UserNotFound,  // user don't have Permissions sub error code =2 (Email or phone number not exist)
    InvalidPassword,  // user enter Wrong password  sub error code =3
    SpaceNotFound,  // user enter Wrong password  sub error code =4 (When send Wrong Space ID)
    InterestFull,  // user try to create space inside interest that is full of spaces  sub error code =5
    BlockFull,  // user try to create space inside Block that is full of spaces  sub error code =6
    DeviceNotFound,
    PostNotFound,  // this will return if user remove post and we request to get deleted post (sub error id =8)
    OwnerBlockedSpace,  //user try to get space info but the owner of the space blocked the logged in  user
    UserBlockedSpace  //user try to get space info but The logged in user blocked the space
}
