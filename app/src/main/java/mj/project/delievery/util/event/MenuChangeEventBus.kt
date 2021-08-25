package mj.project.delievery.util.event

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import mj.project.delievery.screen.main.MainTabMenu

class MenuChangeEventBus {
    // SharedFlow를 사용하면 모든 콘텐츠가 주기적으로 동시에 새로고침되도록 앱의 나머지 부분에 틱을 전송할 수 있습니다.
    val mainTabMenuFlow = MutableSharedFlow<MainTabMenu>()

    suspend fun changeMenu(menu: MainTabMenu) {
        mainTabMenuFlow.emit(menu) // emit( ) : 이벤트를 발생시키는 함수
    }
}
