# MyFinance - Приложение для управления финансами

MyFinance - это приложение для управления финансами, созданное с использованием Android и Firebase. Пользователи могут добавлять друзей, отслеживать свои расходы и делиться финансовой информацией.

# Основные функции

- **Хранение данных на устройстве**: Некоторые данные о пользователе хранятся локально и синхронизируются с облачной базой данных.
- **Firebase**: Приложение использует Firebase для облачного хранения и аутентификации пользователей.
- **DAO (Data Access Object)**: Архитектурный паттерн, который обеспечивает абстракцию взаимодействия с базой данных.
- **Кастомные View**: Уникальные пользовательские интерфейсы, созданные для улучшения взаимодействия с приложением.
- **UI (Пользовательский интерфейс)**: Интуитивно понятный и привлекательный интерфейс, который упрощает доступ к функциям приложения.
- **Custom Adapter**: Пользовательские адаптеры RecyclerView, которые обеспечивают более гибкое отображение данных.


## Activity

Приложение включает в себя следующие активности:

1. **MainActivity**  
   Позволяет просматривать свои расходы и доходы за день, месяц и год. При нажатии на запись можно увидеть ее детали. Также включает навигационную панель для удобства использования.

2. **ProfileActivity**  
   Возможность обновить фото, логин и пароль. Пользователь может удалить свою аватарку и выйти из аккаунта.

3. **MyFriendActivity**  
   Позволяет просматривать список друзей, с возможностью нажатия для просмотра деталей профиля друга. Также включает функцию удаления друга из списка.

4. **AddFriendActivity**  
   Позволяет просматривать всех пользователей приложения и добавлять друзей с помощью поисковой строки.

5. **AddWasteAndIncomeActivity**  
   Предоставляет возможность добавить записи о расходах и доходах.

6. **RegisterActivity**  
   Страница для регистрации нового аккаунта.

7. **LoginActivity**  
   Страница для входа в существующий аккаунт.

8. **ForgotPasswordActivity**  
   Позволяет пользователю обновить забытый пароль.
