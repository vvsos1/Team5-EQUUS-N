export const filterNotifications = (notifications) => {
  const filteredNotis = notifications.filter((notification) => {
    if (notification.read) return false; // 읽은 알람이면 무시
    if (notification.type === 'feedbackReceive') return true;
    if (notification.type === 'feedbackReportCreate') return true;
    if (notification.type === 'frequentFeedbackRequest') return true;
    if (notification.type === 'unreadFeedbackExist') return true;
    return false;
  });

  const uniqueNotis = [];
  const typesSet = new Set();
  const feedbackRequestNotiIds = [];

  filteredNotis.forEach((notification) => {
    if (!typesSet.has(notification.type)) {
      typesSet.add(notification.type);
      uniqueNotis.push(notification);
    }

    if (notification.type === 'frequentFeedbackRequest') {
      feedbackRequestNotiIds.push(notification.notificationId);
    }
  });

  return { notifications: uniqueNotis, feedbackRequestNotiIds };
};
