import { Snackbar, Alert } from '@mui/material';
import { useState } from 'react';

interface NotificationState {
  open: boolean;
  message: string;
  severity: "success" | "error" | "info";
}

interface NotificationSystemProps {
  notification: NotificationState;
  onClose: () => void;
}

export default function NotificationSystem({ notification, onClose }: NotificationSystemProps) {
  return (
    <Snackbar
      open={notification.open}
      autoHideDuration={6000}
      onClose={onClose}
    >
      <Alert 
        onClose={onClose} 
        severity={notification.severity}
        sx={{ width: '100%' }}
      >
        {notification.message}
      </Alert>
    </Snackbar>
  );
}

// Hook for managing notifications
export function useNotification() {
  const [notification, setNotification] = useState<NotificationState>({
    open: false,
    message: "",
    severity: "info"
  });

  const showNotification = (message: string, severity: "success" | "error" | "info" = "info") => {
    setNotification({
      open: true,
      message,
      severity
    });
  };

  const hideNotification = () => {
    setNotification(prev => ({ ...prev, open: false }));
  };

  return {
    notification,
    showNotification,
    hideNotification
  };
} 