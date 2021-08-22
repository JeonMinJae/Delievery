package mj.project.delievery.util.event

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import mj.project.delievery.screen.main.MainTabMenu

class MenuChangeEventBus {
    // SharedFlow를 사용하면 모든 콘텐츠가 주기적으로 동시에 새로고침되도록 앱의 나머지 부분에 틱을 전송할 수 있습니다. 최신 뉴스를 가져오는 것 외에도 좋아하는 주제 컬렉션으로 사용자 정보 섹션을 새로고침할 수도 있습니다
    val mainTabMenuFlow = MutableSharedFlow<MainTabMenu>()

    suspend fun changeMenu(menu: MainTabMenu) {
        mainTabMenuFlow.emit(menu)
    }
}
